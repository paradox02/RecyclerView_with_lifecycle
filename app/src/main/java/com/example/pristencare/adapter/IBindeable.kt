package com.example.pristencare.adapter

import android.view.View
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleRegistry

interface IBindeable {
    fun getItemView():View
    fun getLifeCycleObserver(): LifecycleObserver
    fun bindLifeCycleToView(lifecycleRegistry: LifecycleRegistry)
}