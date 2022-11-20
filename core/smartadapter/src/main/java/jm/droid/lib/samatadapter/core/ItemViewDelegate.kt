package jm.droid.lib.samatadapter.core

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

abstract class ItemViewDelegate<T, VH : ViewHolder> {

    abstract fun onCreateViewHolder(context: Context, parent: ViewGroup): VH

    abstract fun onBindViewHolder(holder: VH, item: T)

    open fun onBindViewHolder(holder: VH, item: T, payloads: List<Any>) {
        onBindViewHolder(holder, item)
    }

    @Suppress("UNUSED_PARAMETER")
    open fun getItemId(item: T): Long = RecyclerView.NO_ID

    open fun onViewRecycled(holder: VH) {}

    open fun onFailedToRecycleView(holder: VH): Boolean {
        return false
    }

    open fun onViewAttachedToWindow(holder: VH) {}

    open fun onViewDetachedFromWindow(holder: VH) {}
}
