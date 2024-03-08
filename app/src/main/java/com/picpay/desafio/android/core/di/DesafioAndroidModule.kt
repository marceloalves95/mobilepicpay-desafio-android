package com.picpay.desafio.android.core.di

import com.picpay.desafio.android.data.BASE_URL
import com.picpay.desafio.android.data.DesafioAndroidRepositoryImpl
import com.picpay.desafio.android.data.api.PicPayService
import com.picpay.desafio.android.domain.repository.DesafioAndroidRepository
import com.picpay.desafio.android.domain.usecases.GetUsersUseCase
import com.picpay.desafio.android.network.others.Interceptor
import com.picpay.desafio.android.network.service.Service
import com.picpay.desafio.android.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
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
        factory<PicPayService> {
            Service(context = get()).createService(baseUrl = BASE_URL)
        }
        factory {
            Interceptor(context = get())
        }
        factory<DesafioAndroidRepository> {
            DesafioAndroidRepositoryImpl(service = get())
        }
    }

    private fun domainModule(): Module = module {
        single {
            GetUsersUseCase(repository = get())
        }
    }

    private fun presentationModule(): Module = module {
        viewModel {
            MainViewModel(getUsersUseCase = get())
        }
    }
}