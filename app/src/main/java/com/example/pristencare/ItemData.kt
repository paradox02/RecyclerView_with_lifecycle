package com.example.pristencare

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.*
import com.example.pristencare.adapter.IBindeable
import com.example.pristencare.databinding.ImageItemBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class ItemData @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val tick: Flow<Int>
) : FrameLayout(context, attributeSet, defStyleAttr), IBindeable, LifecycleObserver {

    lateinit var lifecycleRegistry: Lifecycle

    private val binding = ImageItemBinding.inflate(LayoutInflater.from(context), this, true)

    private val disposableJobs: MutableList<Job> = mutableListOf()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        Log.i("TAG", "onCreate:  ")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        if (::lifecycleRegistry.isInitialized) {
            lifecycleRegistry.coroutineScope.launch {
                Log.i("TAG", "[LIFECYCLE]::onStart Coroutine: (((((((((((((((((( )))))))))))))) ")

                lifecycleRegistry.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    Log.i("TAG", "[LIFECYCLE]::[onResume]")
                    tick.collectLatest {
                        Log.i("TAG", "[ItemData]::[onResume]::[$it]")
                        binding.textView.text = "$it"
                    }
                }
            }.also {
                disposableJobs.add(it)
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        Log.i("TAG", "ON_START:position ====     ")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        Log.i("TAG", "ON_DESTROY: position ====     ")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        Log.i("TAG", "ON_STOP:position ====        ")
        disposableJobs.forEach { job ->
            job.cancel()
        }
        disposableJobs.clear()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Log.i("TAG", "ON_PAUSE:position ====        ")

    }

    fun doCancel(position: Int) {
        Log.i("TAG", "doCancel:position ====  $position ")
    }


    override fun getItemView(): View {
        return this
    }

    override fun getLifeCycleObserver(): LifecycleObserver {
        return this
    }

    override fun bindLifeCycleToView(lifecycleRegistry: LifecycleRegistry) {
        this.lifecycleRegistry = lifecycleRegistry


    }
}