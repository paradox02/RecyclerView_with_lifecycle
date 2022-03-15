package com.example.pristencare.adapter

import android.util.Log
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

open class BaseViewHolder(val bindItemView: IBindeable) :
    RecyclerView.ViewHolder(bindItemView.getItemView()), LifecycleOwner {

    private val lifecycleRegistry = LifecycleRegistry(this)

    init {
        lifecycleRegistry.currentState = Lifecycle.State.INITIALIZED
        lifecycleRegistry.currentState = Lifecycle.State.CREATED /// ON_CREATE EVENTS
        Log.i("init", ": Called ---------->> ")
    }

    fun bind(position: Int) {
        Log.d(
            "bind",
            "bind:  -============================================>>>>    ${lifecycleRegistry.currentState} "
        )
        Log.i(
            "bind",
            "bind:  -============================================>>>>   $absoluteAdapterPosition"
        )
        bindItemView.bindLifeCycleToView(lifecycleRegistry)
//        itemData[absoluteAdapterPosition].lifecycleRegistry = lifecycleRegistry
//        itemData[absoluteAdapterPosition].position = absoluteAdapterPosition
        lifecycleRegistry.addObserver(bindItemView.getLifeCycleObserver())
//            binding.executePendingBindings()
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }


    fun setOnStart() {
        Log.i("TAG", "SET - ON_START:position ====        ${getAbsoluteAdapterPosition()} ")
        lifecycleRegistry.currentState = Lifecycle.State.STARTED     //
        lifecycleRegistry.currentState = Lifecycle.State.RESUMED     //   ON_RESUME EVENT
    }

    fun setOnStop() {
        Log.i("TAG", "SET - ON_STOP:position ====        ${getAbsoluteAdapterPosition()} ")
        lifecycleRegistry.currentState = Lifecycle.State.STARTED    //
        lifecycleRegistry.currentState = Lifecycle.State.CREATED    //     ON_STOP EVENT
    }

    fun setOnDestroy() {
        Log.i("TAG", "SET - ON_DESTROY:position ====        ${getAbsoluteAdapterPosition()} ")
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED   ///  ON_DESTROY EVENT
        doCancel(getAbsoluteAdapterPosition())
    }

    fun doCancel(position: Int) {
        Log.i("TAG", "doCancel:position ====  ${getAbsoluteAdapterPosition()} ")
        lifecycleRegistry.coroutineScope.cancel()
    }
}