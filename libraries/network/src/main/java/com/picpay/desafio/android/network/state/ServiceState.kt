package com.picpay.desafio.android.network.state

import okhttp3.ResponseBody

sealed class ServiceState<out T : Any> {
    data class Success<out T : Any>(
        val data: T,
        val httpCode: Int? = null,
    ) : ServiceState<T>()
    data class Error<out T : Any>(
        val response: T?,
        val message:String? = null,
        val httpCode: Int? = null,
        val errorBody: ResponseBody? = null,
        val exception: Throwable,
    ) : ServiceState<T>()
}