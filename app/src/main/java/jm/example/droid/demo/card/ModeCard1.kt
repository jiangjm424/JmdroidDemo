package jm.example.droid.demo.card

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import jm.droid.lib.samatadapter.core.ViewBindingDelegate
import jm.example.droid.demo.data.ModeTest1
import jm.example.droid.demo.databinding.ModeCard1Binding

class ModeCard1 : ViewBindingDelegate<ModeTest1, ModeCard1Binding>() {

    override fun onBindItem(view: ModeCard1Binding, item: ModeTest1) {
        view.textView.text = "mode:${item.d1}"
        view.textView2.text = "mode:${item.s1}"
    }

    override fun onCreateViewBinding(context: Context, parent: ViewGroup): ModeCard1Binding {
        return ModeCard1Binding.inflate(LayoutInflater.from(context))
    }
}