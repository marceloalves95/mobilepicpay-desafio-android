package com.picpay.desafio.android


class MainActivityTest {

//    private val server = MockWebServer()
//
//    private val context = InstrumentationRegistry.getInstrumentation().targetContext
//
//    @Test
//    fun shouldDisplayTitle() {
//        launchActivity<MainActivity>().apply {
//            val expectedTitle = context.getString(R.string.title)
//
//            moveToState(Lifecycle.State.RESUMED)
//
//            onView(withText(expectedTitle)).check(matches(isDisplayed()))
//        }
//    }
//
//    @Test
//    fun shouldDisplayListItem() {
//        server.dispatcher = object : Dispatcher() {
//            override fun dispatch(request: RecordedRequest): MockResponse {
//                return when (request.path) {
//                    "/users" -> successResponse
//                    else -> errorResponse
//                }
//            }
//        }
//
//        server.start(serverPort)
//
//        launchActivity<MainActivity>().apply {
//            // TODO("validate if list displays items returned by server")
//        }
//
//        server.close()
//    }
//
//    companion object {
//        private const val serverPort = 8080
//
//        private val successResponse by lazy {
//            val body =
//                "[{\"id\":1001,\"name\":\"Eduardo Santos\",\"img\":\"https://randomuser.me/api/portraits/men/9.jpg\",\"username\":\"@eduardo.santos\"}]"
//
//            MockResponse()
//                .setResponseCode(200)
//                .setBody(body)
//        }
//
//        private val errorResponse by lazy { MockResponse().setResponseCode(404) }
//    }
}