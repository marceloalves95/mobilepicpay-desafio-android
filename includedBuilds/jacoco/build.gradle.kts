plugins {
    `kotlin-dsl`
}
repositories {
    mavenCentral()
    google()
}
dependencies {
    implementation("com.android.tools.build:gradle:8.1.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
}
gradlePlugin {
    // register JacocoReportsPlugin as a plugin
    plugins {
        register("jacoco-reports") {
            id = "jacoco-reports"
            implementationClass = "JacocoReportsPlugin"
        }
    }
}