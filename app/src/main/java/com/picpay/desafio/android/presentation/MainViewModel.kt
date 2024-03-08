package com.picpay.desafio.android.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.picpay.desafio.android.domain.usecases.GetUsersUseCase
import com.picpay.desafio.android.extensions.others.launch
import com.picpay.desafio.android.network.event.Event
import com.picpay.desafio.android.presentation.state.DesafioAndroidState

class MainViewModel(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private val _state = MutableLiveData<DesafioAndroidState>()
    val state: LiveData<DesafioAndroidState> get() = _state

    fun getUsers() = launch {
        getUsersUseCase.invoke().collect { event ->
            when (event) {
                is Event.Loading -> {
                    _state.value = DesafioAndroidState.Loading
                }

                is Event.Data -> {
                    _state.value = DesafioAndroidState.ScreenData(event.data)
                }

                is Event.Error -> {
                    _state.value = DesafioAndroidState.Error(event.error)
                }

                else -> Unit
            }
        }
    }
}