package com.example.pristencare.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pristencare.ItemData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
class RecyclerViewImageAdapter(
    val lifecycle: LifecycleOwner,
    val tickFlow: Flow<Int>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val itemData = arrayListOf<ItemData>()
    private var recyclerView: RecyclerView? = null


    init {
        lifecycle.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                recyclerView?.let { parent ->
                    val childCount = parent.childCount
                    for (i in 0 until childCount) {
                        parent.findViewHolderForLayoutPosition(i)?.let {
                            (it as BaseViewHolder).setOnDestroy()
                        }
                    }
                }

            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            fun onStop() {
                recyclerView?.let { parent ->
                    val childCount = parent.childCount
                    for (i in 0 until childCount) {
                        parent.findViewHolderForLayoutPosition(i)?.let {
                            (it as BaseViewHolder).setOnStop()
                        }
                    }
                }
            }


            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun onStart() {
                recyclerView?.run {
                    if (layoutManager is LinearLayoutManager) {
                        val first =
                            (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                        val last =
                            (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                        if (first in 0..last)
                            for (i in first..last) {
                                findViewHolderForAdapterPosition(i)?.let {
                                    (it as BaseViewHolder).setOnStart()
                                }
                            }
                    }
                }
            }
        })

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemData(parent.context, null, 0, tickFlow)
        return BaseViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemData.count()
    }

    fun setListData(itemData: List<ItemData>) {
        this.itemData.addAll(itemData)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BaseViewHolder).bind(position)
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        Log.i("TAG", "onViewAttachedToWindow ====  ${holder.getAbsoluteAdapterPosition()} ")
        (holder as BaseViewHolder).setOnStart()
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        Log.i("TAG", "onViewDetachedFromWindow ====  ${holder.getAbsoluteAdapterPosition()} ")
        (holder as BaseViewHolder).setOnStop()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView = null
    }

}