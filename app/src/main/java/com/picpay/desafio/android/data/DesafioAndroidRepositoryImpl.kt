package com.picpay.desafio.android.data

import com.picpay.desafio.android.data.api.PicPayService
import com.picpay.desafio.android.data.mapper.toUsers
import com.picpay.desafio.android.domain.models.Users
import com.picpay.desafio.android.domain.repository.DesafioAndroidRepository
import com.picpay.desafio.android.extensions.network.parseResponse
import com.picpay.desafio.android.extensions.network.toResponse

class DesafioAndroidRepositoryImpl(
    private val service: PicPayService
) : DesafioAndroidRepository {
    override suspend fun getUsers(): List<Users> {
        return service.getUsers().parseResponse().toResponse().map { it.toUsers() }
    }

}