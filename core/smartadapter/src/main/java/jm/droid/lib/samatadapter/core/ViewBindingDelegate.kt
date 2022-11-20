package jm.droid.lib.samatadapter.core

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import jm.droid.lib.samatadapter.R

abstract class ViewBindingDelegate<T, VB : ViewBinding> : ItemViewDelegate<T, ViewBindingDelegate.Holder<VB>>() {

  protected val View.holder: Holder<VB>
    get() {
    return holder(this) ?: throw IllegalAccessException("The view holder property can only be called after onCreateView()!")
  }

  protected val View.layoutPosition: Int get() = holder.layoutPosition

  protected val View.absoluteAdapterPosition: Int get() = holder.absoluteAdapterPosition

  protected val View.bindingAdapterPosition: Int get() = holder.bindingAdapterPosition

  abstract fun onCreateViewBinding(context: Context, parent: ViewGroup): VB

  abstract fun onBindItem(view: VB, item: T)

  // Override this function if you need a ViewHolder or positions
  open fun onBindItem(holder: Holder<VB>, view: VB, item: T) {
    view.root.setTag(R.id.tag_view_holder, holder)
    onBindItem(view, item)
  }

  protected fun getRecyclerLayoutParams(view: View): RecyclerView.LayoutParams {
    return (view.layoutParams as RecyclerView.LayoutParams)
  }

  private fun holder(view: View): Holder<VB>? {
    @Suppress("UNCHECKED_CAST")
    return view.getTag(R.id.tag_view_holder) as? Holder<VB>
  }

  override fun onCreateViewHolder(context: Context, parent: ViewGroup): Holder<VB> {
    return Holder(onCreateViewBinding(context, parent))
  }

  override fun onBindViewHolder(holder: Holder<VB>, item: T) = onBindItem(holder, holder.binding, item)

  class Holder<V : ViewBinding>(val binding: V) : RecyclerView.ViewHolder(binding.root)
}
