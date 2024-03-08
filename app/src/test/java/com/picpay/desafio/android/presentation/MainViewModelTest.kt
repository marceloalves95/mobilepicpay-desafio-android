package com.picpay.desafio.android.presentation

import androidx.lifecycle.Observer
import com.picpay.desafio.android.domain.model.dummyListUsers
import com.picpay.desafio.android.domain.usecases.GetUsersUseCase
import com.picpay.desafio.android.network.event.Event
import com.picpay.desafio.android.presentation.state.DesafioAndroidState
import com.picpay.desafio.android.testing.BaseTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainViewModelTest : BaseTest(){

    @RelaxedMockK
    private lateinit var getUsersUseCase: GetUsersUseCase

    @RelaxedMockK
    private lateinit var stateObserver: Observer<DesafioAndroidState>

    @RelaxedMockK
    private lateinit var viewModel: MainViewModel

    @Before
    fun setup(){
        viewModel = MainViewModel(
            getUsersUseCase = getUsersUseCase
        ).also {
            with(viewModel){
                state.observeForever(stateObserver)
            }
        }
    }

    @After
    fun tearDown(){
        with(viewModel){
            state.removeObserver(stateObserver)
        }
    }

    @Test
    fun `should get users when it is called with success`() = runBlocking {

        //Arrange
        val state = DesafioAndroidState.ScreenData(dummyListUsers)

        coEvery { getUsersUseCase.invoke() } returns flowOf(
            Event.loading(isLoading = true),
            Event.data(dummyListUsers)
        )

        coEvery {
            stateObserver.onChanged(state)
        } returns Unit

        //Act
        viewModel.getUsers()

        //Assert
        coVerify(exactly = 1) {
            getUsersUseCase.invoke()
        }

        confirmVerified(getUsersUseCase)
    }

    @Test
    fun `should get users when it is called with failure`() = runBlocking {

        //Arrange
        val error = mockk<Throwable>(relaxed = true)
        val state = DesafioAndroidState.Error(error = error)

        coEvery { getUsersUseCase.invoke() } returns flowOf(
            Event.loading(isLoading = true),
            Event.error(error)
        )

        coEvery {
            stateObserver.onChanged(state)
        } returns Unit

        //Act
        viewModel.getUsers()

        //Assert
        coVerify(exactly = 1) {
            getUsersUseCase.invoke()
        }

        confirmVerified(getUsersUseCase)

    }

}
