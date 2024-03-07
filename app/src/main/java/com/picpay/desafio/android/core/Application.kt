package com.picpay.desafio.android.core

import android.app.Application
import com.picpay.desafio.android.core.di.DesafioAndroidModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
        }
        DesafioAndroidModule.load()
    }
}