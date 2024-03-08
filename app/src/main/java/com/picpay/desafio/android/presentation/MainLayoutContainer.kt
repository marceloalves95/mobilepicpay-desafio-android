package com.picpay.desafio.android.presentation

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.domain.models.Users
import com.picpay.desafio.android.presentation.adapter.UserListAdapter
import com.picpay.desafio.android.presentation.state.DesafioAndroidState

class MainLayoutContainer(
    private val binding: ActivityMainBinding,
    private val context: Context
) {

    private var listAdapter = UserListAdapter()
    fun setState(state: DesafioAndroidState) {
        when (state) {
            is DesafioAndroidState.ScreenData -> handleScreenData(state.screenData)
            is DesafioAndroidState.Error -> handleErrorData(state.error)
            else -> Unit
        }
        handleLoading()
    }

    private fun handleLoading() {
        binding.userListProgressBar.visibility = View.VISIBLE
    }

    private fun handleErrorData(exception: Throwable?) = with(binding) {
        userListProgressBar.visibility = View.GONE
        recyclerView.visibility = View.GONE
        Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show()
        Log.d("error", "${exception?.message}")
    }

    private fun handleScreenData(screenData: List<Users>) {
        binding.recyclerView.apply {
            listAdapter = UserListAdapter()
            listAdapter.listUsers = screenData
            adapter = listAdapter
        }
    }

}