package com.picpay.desafio.android.core.di

import org.koin.core.context.GlobalContext.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DesafioAndroidModule {
    fun load() {
        loadKoinModules(
            listOf(
                dataModule(),
                domainModule(),
                presentationModule()
            )
        )
    }

    private fun dataModule(): Module = module {

    }

    private fun domainModule(): Module = module {

    }

    private fun presentationModule(): Module = module {

    }
}