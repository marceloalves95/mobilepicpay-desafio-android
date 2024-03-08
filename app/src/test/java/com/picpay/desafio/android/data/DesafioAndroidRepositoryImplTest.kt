package com.picpay.desafio.android.data

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.picpay.desafio.android.data.api.PicPayService
import com.picpay.desafio.android.data.mapper.toUsers
import com.picpay.desafio.android.data.model.dummyListUsersResponse
import com.picpay.desafio.android.data.models.UsersResponse
import com.picpay.desafio.android.testing.BaseTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class DesafioAndroidRepositoryImplTest : BaseTest() {

    @RelaxedMockK
    private lateinit var repository: DesafioAndroidRepositoryImpl

    @RelaxedMockK
    private lateinit var service: PicPayService

    @Before
    fun setup() {
        repository = DesafioAndroidRepositoryImpl(service)
    }

    @Test
    fun `should get users when is called with success`() = runBlocking {

        //Arrange
        val response = Response.success(dummyListUsersResponse)

        coEvery {
            service.getUsers()
        } returns response

        //Act
        val result = repository.getUsers()

        //Assert
        assertThat(result).isEqualTo(dummyListUsersResponse.map { it.toUsers() })

        coVerify(exactly = 1) {
            service.getUsers()
        }

        confirmVerified(service)

    }

    @Test(expected = HttpException::class)
    fun `should get countries when is called with failure`() = runBlocking {

        //Arrange
        val responseError = Response.error<UsersResponse>(
            500,
            "some content".toResponseBody("plain/text".toMediaTypeOrNull())
        )

        coEvery {
            service.getUsers()
        } throws HttpException(responseError)

        //Act
        repository.getUsers()

        //Assert
        coVerify(exactly = 1) {
            service.getUsers()
        }

        confirmVerified(service)
    }

}
