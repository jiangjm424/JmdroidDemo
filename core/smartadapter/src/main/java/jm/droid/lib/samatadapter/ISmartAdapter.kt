package jm.droid.lib.samatadapter

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import jm.droid.lib.samatadapter.core.ItemViewDelegate
import jm.droid.lib.samatadapter.core.MutableTypes
import jm.droid.lib.samatadapter.core.SmartAdapterImpl
import jm.droid.lib.samatadapter.core.Type

interface ISmartAdapter {
    companion object {
        private const val TAG = "ISmartAdapter"
    }

    val adapter: RecyclerView.Adapter<*>

    fun setData(data: List<Any>)

    fun add(data:List<Any>)

    fun remove(pos:Int):Any

    fun update(pos: Int, data:Any)

    class Builder {
        private val types = MutableTypes()
        private fun unregisterAllTypesIfNeeded(clazz: Class<*>) {
            if (types.unregister(clazz)) {
                Log.w(TAG,"The type ${clazz.simpleName} you originally registered is now overwritten.")
            }
        }

        fun <T> register(clazz: Class<T>, delegate: ItemViewDelegate<T,*>) = apply {
            unregisterAllTypesIfNeeded(clazz)
            register(Type(clazz, delegate))
        }

        inline fun <reified T : Any> register(delegate: ItemViewDelegate<T, *>)  = apply {
            register(T::class.java, delegate)
        }

        private fun <T> register(type: Type<T>)  = apply {
            types.register(type)
        }

        fun build(): ISmartAdapter {
            return SmartAdapterImpl(types)
        }
    }
}