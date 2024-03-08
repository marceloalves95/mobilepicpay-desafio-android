package com.picpay.desafio.android.extensions.views

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T
) = lazy(LazyThreadSafetyMode.NONE) {
    bindingInflater.invoke(layoutInflater)
}

fun <T : ViewBinding> Fragment.viewBinding(
    bind: (View) -> T
) = FragmentAutoClearedValueBinding(bind)

class FragmentAutoClearedValueBinding<T : ViewBinding>(
    val binder: (View) -> T
) : ReadOnlyProperty<Fragment, T>,
    DefaultLifecycleObserver {

    private var value: T? = null

    override fun onDestroy(owner: LifecycleOwner) {
        value = null // Clear reference.
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return value ?: binder(thisRef.requireView()).also {
            setValue(thisRef, it)
        }
    }

    private fun setValue(fragment: Fragment, value: T) {
        fragment.viewLifecycleOwner.lifecycle.removeObserver(this)
        this.value = value
        fragment.viewLifecycleOwner.lifecycle.addObserver(this)
    }
}