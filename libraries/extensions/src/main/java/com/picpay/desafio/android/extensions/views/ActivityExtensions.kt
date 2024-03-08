package com.picpay.desafio.android.extensions.views

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

inline fun <reified T> AppCompatActivity.observe(
    liveData: LiveData<T>,
    noinline lifecycle:(T) -> Unit){
    liveData.observe(this, Observer(lifecycle))
}