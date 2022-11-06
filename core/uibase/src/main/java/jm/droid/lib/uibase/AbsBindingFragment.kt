package jm.droid.lib.uibase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding

abstract class AbsBindingFragment<VB : ViewBinding> : BaseFragment() {

    private var _binding: VB? = null
    private val binding: VB get() = _binding!!

    private val viewLifecycleScope get() = viewLifecycleOwner.lifecycleScope

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ViewBindingUtil.inflateWithGeneric(this, inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(binding, savedInstanceState)
        setupData(binding, viewLifecycleOwner)
        viewLifecycleScope.launchWhenResumed {
            onPageFirstComing()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun setupView(binding: VB, savedInstanceState: Bundle?)

    abstract fun setupData(binding: VB, owner: LifecycleOwner)

    open fun onPageFirstComing() {}
}