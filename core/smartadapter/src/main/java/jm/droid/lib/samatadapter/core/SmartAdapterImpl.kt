package jm.droid.lib.samatadapter.core

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jm.droid.lib.samatadapter.ISmartAdapter

class SmartAdapterImpl internal constructor(private val types: MutableTypes) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), ISmartAdapter {
    private val items: MutableList<Any> = mutableListOf()

    override fun getItemViewType(position: Int): Int {
        return indexInTypesOf(position, items[position])
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        indexViewType: Int
    ): RecyclerView.ViewHolder {
        return types.getType<Any>(indexViewType).delegate.onCreateViewHolder(parent.context, parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindViewHolder(holder, position, emptyList())
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: List<Any>
    ) {
        val item = items[position]
        getOutDelegateByViewHolder(holder).onBindViewHolder(holder, position, item, payloads)
    }

    override fun getItemCount(): Int = items.size

    override fun getItemId(position: Int): Long {
        val item = items[position]
        val itemViewType = getItemViewType(position)
        return types.getType<Any>(itemViewType).delegate.getItemId(item)
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        getOutDelegateByViewHolder(holder).onViewRecycled(holder)
    }

    override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean {
        return getOutDelegateByViewHolder(holder).onFailedToRecycleView(holder)
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        getOutDelegateByViewHolder(holder).onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        getOutDelegateByViewHolder(holder).onViewDetachedFromWindow(holder)
    }

    private fun getOutDelegateByViewHolder(holder: RecyclerView.ViewHolder): ItemViewDelegate<Any, RecyclerView.ViewHolder> {
        @Suppress("UNCHECKED_CAST")
        return types.getType<Any>(holder.itemViewType).delegate as ItemViewDelegate<Any, RecyclerView.ViewHolder>
    }

    @Throws(DelegateNotFoundException::class)
    internal fun indexInTypesOf(position: Int, item: Any): Int {
        val index = types.firstIndexOf(item.javaClass)
        if (index != -1) {
            return index
        }
        throw DelegateNotFoundException(item.javaClass)
    }

    override fun add(data: List<Any>) {
        val positionStart = items.size
        items.addAll(data)
        notifyItemRangeInserted(positionStart, data.size)
    }

    override fun remove(pos: Int): Any {
        val item = items.removeAt(pos)
        notifyItemRemoved(pos)
        return item
    }

    override fun update(pos: Int, data: Any) {
        items[pos] = data
        notifyItemChanged(pos, data)
    }

    override val adapter: RecyclerView.Adapter<*>
        get() = this

    override fun setData(data: List<Any>) {
        items.clear()
        items.addAll(data)
        notifyItemRangeChanged(0, data.size)
    }
}