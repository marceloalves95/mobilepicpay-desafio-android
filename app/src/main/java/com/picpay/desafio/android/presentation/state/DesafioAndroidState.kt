package com.picpay.desafio.android.presentation.state

import com.picpay.desafio.android.domain.models.Users

sealed class DesafioAndroidState {
    data object Loading : DesafioAndroidState()
    data class ScreenData(val screenData: List<Users>) : DesafioAndroidState()
    data class Error(val error: Throwable? = null) : DesafioAndroidState()
}