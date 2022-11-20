
package jm.droid.lib.samatadapter.core
data class Type<T>(
  val clazz: Class<out T>,
  val delegate: ItemViewDelegate<T, *>
)
