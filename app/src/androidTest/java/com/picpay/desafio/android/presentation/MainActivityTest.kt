package com.picpay.desafio.android.presentation

import androidx.test.core.app.ActivityScenario.launch
import androidx.test.platform.app.InstrumentationRegistry
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.picpay.desafio.android.R
import org.junit.Test

class MainActivityTest{

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun shouldDisplayTitle(){
        val expectedTitle = context.getString(R.string.title)
        launch(MainActivity::class.java)
        assertDisplayed(expectedTitle)
    }

}