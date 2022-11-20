package jm.droid.lib.samatadapter.core

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import jm.droid.lib.samatadapter.R

/**
 * This is a simple [ItemViewDelegate] that does not require to declare and provide a [RecyclerView.ViewHolder].
 * @since v4.2.0
 * @author Drakeet Xu
 */
abstract class ViewBindingDelegate<T, VB : ViewBinding> : ItemViewDelegate<T, ViewBindingDelegate.Holder<VB>>() {

  protected val View.holder: Holder<VB>
    get() {
    return holder(this) ?: throw IllegalAccessException("The view holder property can only be called after onCreateView()!")
  }

  protected val View.layoutPosition: Int get() = holder.layoutPosition

  protected val View.absoluteAdapterPosition: Int get() = holder.absoluteAdapterPosition

  protected val View.bindingAdapterPosition: Int get() = holder.bindingAdapterPosition

  abstract fun onCreateView(context: Context): VB

  abstract fun onBindView(view: VB, item: T)

  // Override this function if you need a parent ViewGroup
  open fun onCreateView(context: Context, parent: ViewGroup): VB {
    return onCreateView(context)
  }

  // Override this function if you need a ViewHolder or positions
  open fun onBindView(holder: Holder<VB>, view: VB, item: T) {
    view.root.setTag(R.id.tagViewHolder, holder)
    onBindView(view, item)
  }

  protected fun getRecyclerLayoutParams(view: View): RecyclerView.LayoutParams {
    return (view.layoutParams as RecyclerView.LayoutParams)
  }

  private fun holder(view: View): Holder<VB>? {
    @Suppress("UNCHECKED_CAST")
    return view.getTag(R.id.tagViewHolder) as? Holder<VB>
  }

  override fun onCreateViewHolder(context: Context, parent: ViewGroup): Holder<VB> {
    return Holder(onCreateView(context, parent))
  }

  override fun onBindViewHolder(holder: Holder<VB>, item: T) = onBindView(holder, holder.binding, item)

  class Holder<V : ViewBinding>(val binding: V) : RecyclerView.ViewHolder(binding.root)
}
