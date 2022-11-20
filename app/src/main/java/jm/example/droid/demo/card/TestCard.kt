package jm.example.droid.demo.card

import android.content.Context
import android.view.LayoutInflater
import jm.droid.lib.samatadapter.core.ViewBindingDelegate
import jm.example.droid.demo.databinding.TextCardBinding

class TestCard : ViewBindingDelegate<String, TextCardBinding>() {
    override fun onCreateView(context: Context): TextCardBinding {
        return TextCardBinding.inflate(LayoutInflater.from(context))
    }

    override fun onBindView(view: TextCardBinding, item: String) {
        view.textView.text = "item:$item"
    }
}