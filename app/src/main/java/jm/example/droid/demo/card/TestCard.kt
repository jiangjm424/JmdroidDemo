package jm.example.droid.demo.card

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import jm.droid.lib.samatadapter.core.ViewBindingDelegate
import jm.example.droid.demo.databinding.TextCardBinding

class TestCard : ViewBindingDelegate<String, TextCardBinding>() {

    override fun onBindItem(view: TextCardBinding, item: String) {
        view.textView.text = "item:$item"
    }

    override fun onCreateViewBinding(context: Context, parent: ViewGroup): TextCardBinding {
        return TextCardBinding.inflate(LayoutInflater.from(context), parent, false)
    }
}