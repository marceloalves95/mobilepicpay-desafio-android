import com.android.build.gradle.BaseExtension
import extensions.capitalizeWord
import groovy.xml.XmlSlurper
import groovy.xml.slurpersupport.NodeChild
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.register
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.tasks.JacocoReport
import java.io.File
import java.util.Locale
import kotlin.math.roundToInt

class JacocoReportsPlugin : Plugin<Project> {
    private val Project.android: BaseExtension
        get() = extensions.findByName("android") as? BaseExtension
            ?: error("Not an Android module: $name")

    private val Project.jacoco: JacocoPluginExtension
        get() = extensions.findByName("jacoco") as? JacocoPluginExtension
            ?: error("Not a Jacoco module: $name")

    private val excludedFiles = mutableSetOf(
        "**/R.class",
        "**/R$*.class",
        "**/*\$ViewInjector*.*",
        "**/*\$ViewBinder*.*",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Factory*",
        "**/*_MembersInjector*",
        "**/*Module*",
        "**/*Component*",
        "**android**",
        "**/BR.class",
        "android/**/*.*",
        "**/*Test*.*"
    )

    private val limits = mutableMapOf(
        "instruction" to 0.0,
        "branch" to 0.0,
        "line" to 0.0,
        "complexity" to 0.0,
        "method" to 0.0,
        "class" to 0.0
    )

    override fun apply(project: Project) =
        with(project) {
            plugins.run {
                apply("jacoco")
            }

            extra.set("limits", limits)

            jacocoAfterEvaluate()

            dependencies {
                "implementation"("org.jacoco:org.jacoco.core:0.8.7")
            }
        }

    private fun Project.jacocoAfterEvaluate() = afterEvaluate {
        val buildTypes = android.buildTypes.map { type -> type.name }
        var productFlavors = android.productFlavors.map { flavor -> flavor.name }

        if (productFlavors.isEmpty()) {
            productFlavors = productFlavors + ""
        }

        productFlavors.forEach { flavorName ->
            buildTypes.forEach { buildTypeName ->
                val sourceName: String
                val sourcePath: String

                if (flavorName.isEmpty()) {
                    sourceName = buildTypeName
                    sourcePath = buildTypeName
                } else {
                    sourceName = "${flavorName}${buildTypeName.capitalizeWord(Locale.ENGLISH)}"
                    sourcePath = "${flavorName}/${buildTypeName}"
                }

                val testTaskName = "test${sourceName.capitalizeWord(Locale.ENGLISH)}UnitTest"

                registerCodeCoverageTask(
                    testTaskName = testTaskName,
                    sourceName = sourceName,
                    sourcePath = sourcePath,
                    flavorName = flavorName,
                    buildTypeName = buildTypeName
                )
            }
        }
    }

    private fun Project.registerCodeCoverageTask(
        testTaskName: String,
        sourceName: String,
        sourcePath: String,
        flavorName: String,
        buildTypeName: String
    ) {
        tasks.register<JacocoReport>("${testTaskName}Coverage") {
            dependsOn(testTaskName)
            group = "Reporting"
            description =
                "Generate Jacoco coverage reports on the ${sourceName.capitalizeWord(Locale.ENGLISH)} build."

            val javaDirectories = fileTree(
                "${buildDir}/intermediates/classes/${sourcePath}"
            ) { exclude(excludedFiles) }

            val kotlinDirectories = fileTree(
                "${buildDir}/tmp/kotlin-classes/${sourcePath}"
            ) { exclude(excludedFiles) }

            val coverageSrcDirectories = listOf(
                "src/main/java",
                "src/$flavorName/java",
                "src/$buildTypeName/java"
            )

            classDirectories.setFrom(files(javaDirectories, kotlinDirectories))
            additionalClassDirs.setFrom(files(coverageSrcDirectories))
            sourceDirectories.setFrom(files(coverageSrcDirectories))
            executionData.setFrom(
                fileTree(buildDir).include(
                    "/jacoco/*.exec",
                    "outputs/code-coverage/debugAndroidTest/connected/*coverage.ec"
                )
            )

            reports {
                xml.required.set(true)
                html.required.set(true)
                html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
            }

            doLast {
                jacocoTestReport("${testTaskName}Coverage")
                println("Report generated in file:///${buildDir}\\jacocoHtml")
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun Project.jacocoTestReport(testTaskName: String) {
        val reportsDirectory = jacoco.reportsDirectory.asFile.get()
        val report = file("$reportsDirectory/${testTaskName}/${testTaskName}.xml")

        logger.lifecycle("Checking coverage results: $report")

        val metrics = report.extractTestsCoveredByType()
        val limits = project.extra["limits"] as Map<String, Double>

        val failures = metrics.filter { entry ->
            entry.value < limits[entry.key]!!
        }.map { entry ->
            "- ${entry.key} coverage rate is: ${entry.value}%, minimum is ${limits[entry.key]}%"
        }

        if (failures.isNotEmpty()) {
            logger.quiet("------------------ Code Coverage Failed -----------------------")
            failures.forEach { logger.quiet(it) }
            logger.quiet("---------------------------------------------------------------")
            throw GradleException("Code coverage failed")
        }

        logger.quiet("------------------ Code Coverage Success -----------------------")
        metrics.forEach { entry ->
            logger.quiet("- ${entry.key} coverage rate is: ${entry.value}%")
        }
        logger.quiet("---------------------------------------------------------------")
    }

    @Suppress("UNCHECKED_CAST")
    private fun File.extractTestsCoveredByType(): Map<String, Double> {
        val xmlReader = XmlSlurper().apply {
            setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
            setFeature("http://apache.org/xml/features/disallow-doctype-decl", false)
        }

        val counterNodes: List<NodeChild> = xmlReader
            .parse(this).parent()
            .children()
            .filter {
                (it as NodeChild).name() == "counter"
            } as List<NodeChild>

        return counterNodes.associate { nodeChild ->
            val type = nodeChild.attributes()["type"].toString().lowercase(Locale.ENGLISH)

            val covered = nodeChild.attributes()["covered"].toString().toDouble()
            val missed = nodeChild.attributes()["missed"].toString().toDouble()
            val percentage = ((covered / (covered + missed)) * 10000.0).roundToInt() / 100.0

            Pair(type, percentage)
        }
    }
}