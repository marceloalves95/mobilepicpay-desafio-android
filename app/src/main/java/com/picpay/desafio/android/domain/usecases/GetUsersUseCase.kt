package com.picpay.desafio.android.domain.usecases

import com.picpay.desafio.android.domain.repository.DesafioAndroidRepository
import com.picpay.desafio.android.extensions.others.executeFlow

class GetUsersUseCase(
    private val repository: DesafioAndroidRepository
) {
    suspend operator fun invoke() = executeFlow(
        getRepository = {
            repository.getUsers()
        }
    )
}