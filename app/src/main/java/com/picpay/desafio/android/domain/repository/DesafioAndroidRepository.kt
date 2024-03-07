package com.picpay.desafio.android.domain.repository

import com.picpay.desafio.android.domain.models.Users

interface DesafioAndroidRepository {
    suspend fun getUsers(): List<Users>
}