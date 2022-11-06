package jm.droid.lib.uibase

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding

abstract class AbsBindingActivity<VB : ViewBinding> : BaseActivity() {
    private lateinit var viewBinding: VB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ViewBindingUtil.inflateWithGeneric(this, layoutInflater)
        setContentView(viewBinding.root)
        setupView(viewBinding, savedInstanceState)
        setupData(this)
    }

    abstract fun setupView(binding: VB, savedInstanceState: Bundle?)

    abstract fun setupData(owner: LifecycleOwner)
}