package com.picpay.desafio.android.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.extensions.views.observe
import com.picpay.desafio.android.extensions.views.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val layoutContainer by lazy {
        MainLayoutContainer(binding, this)
    }
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.getUsers()
        setupObserves()
    }

    private fun setupObserves() = with(viewModel) {
        observe(state) {
            layoutContainer.setState(it)
        }
    }
}
