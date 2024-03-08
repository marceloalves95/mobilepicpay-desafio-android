package com.picpay.desafio.android.domain.usecases

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.picpay.desafio.android.domain.model.dummyListUsers
import com.picpay.desafio.android.domain.models.Users
import com.picpay.desafio.android.domain.repository.DesafioAndroidRepository
import com.picpay.desafio.android.network.event.Event
import com.picpay.desafio.android.testing.BaseTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetUsersUseCaseTest : BaseTest() {

    @RelaxedMockK
    private lateinit var getUsersUseCase: GetUsersUseCase

    @MockK
    private lateinit var repository: DesafioAndroidRepository

    @Before
    fun setup() {
        getUsersUseCase = GetUsersUseCase(repository)
    }

    @Test
    fun `should call usecase when it is called with success`() = runBlocking {

        //Arrange
        val progressEmit: MutableList<Event<List<Users>>> = mutableListOf()

        coEvery {
            repository.getUsers()
        } returns dummyListUsers

        //Act
        getUsersUseCase.invoke().collect { event ->
            progressEmit.add(event)
        }

        //Assert
        assertThat(progressEmit).isEqualTo(
            mutableListOf(
                Event.Loading,
                Event.Data(dummyListUsers)
            )
        )

        coVerify {
            repository.getUsers()
        }

        confirmVerified(repository)
    }

    @Test(expected = Throwable::class)
    fun `should call usecase when it is called with failure`() = runBlocking {

        //Arrange
        val progressEmit: MutableList<Event<List<Users>>> = mutableListOf()
        val error = mockk<Throwable>(relaxed = true)

        coEvery {
            repository.getUsers()
        } throws error

        //Act
        getUsersUseCase.invoke().collect { event ->
            progressEmit.add(event)
        }

        //Assert
        assertThat(progressEmit).isEqualTo(
            mutableListOf(
                Event.Loading,
                Event.Error(error)
            )
        )

        coVerify {
            repository.getUsers()
        }

        confirmVerified(repository)
    }

}