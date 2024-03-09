package com.picpay.desafio.android.presentation

import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.interaction.BaristaSleepInteractions.sleep
import com.picpay.desafio.android.FileReader
import com.picpay.desafio.android.R
import com.picpay.desafio.android.RecyclerViewMatchers
import com.picpay.desafio.android.data.api.PicPayService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivityTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val mockWebServer = MockWebServer()
    private val mockResponse = MockResponse()
    private lateinit var service: PicPayService

    @Before
    fun setup() {
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PicPayService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testSuccessfulResponse() {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse().setResponseCode(200)
                    .setBody(FileReader.readStringFromFile("success_response.json"))
            }
        }
        mockWebServer.start(8080)
        launch(MainActivity::class.java)
        sleep(2000)
        RecyclerViewMatchers.checkRecyclerViewItem(R.id.recyclerView, 0, withId(R.id.username))
        mockWebServer.close()
    }

    @Test
    fun testErrorResponse() {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse().setResponseCode(404)
            }
        }
        mockWebServer.start(8080)
        launch(MainActivity::class.java)
        sleep(2000)
        mockWebServer.close()
    }

    @Test
    fun testBodyDifferentOfEmpty() = runBlocking {

        //Arrange
        mockResponse.setResponseCode(200)
            .setBody(FileReader.readStringFromFile("success_response.json"))
        mockWebServer.enqueue(mockResponse)

        //Act
        val response = service.getUsers()
        mockWebServer.takeRequest()

        //Assert
        assertThat(false).isEqualTo(response.body()?.isEmpty())
    }

    @Test
    fun shouldDisplayTitle() {
        val expectedTitle = context.getString(R.string.title)
        launch(MainActivity::class.java)
        assertDisplayed(expectedTitle)
    }
}