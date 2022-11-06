package jm.droid.lib.uibase

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    companion object {
        private const val TAG = "BaseFragment"
        private const val DEBUG = true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        log("onAttach:$this")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("onCreate:$this")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        log("onCreateView:$this")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        log("onViewCreated:$this")
    }

    override fun onStart() {
        super.onStart()
        log("onStart:$this")
    }

    override fun onResume() {
        super.onResume()
        log("onResume:$this")
    }

    override fun onPause() {
        super.onPause()
        log("onPause:$this")
    }

    override fun onStop() {
        super.onStop()
        log("onStop:$this")
    }

    override fun onDetach() {
        super.onDetach()
        log("onDetach:$this")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        log("onDestroyView:$this")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy:$this")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        log("onSaveInstanceState:$this")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        log("onConfigurationChanged:$this")
    }

    private fun log(msg: String) {
        if (DEBUG)
            Log.v(TAG, msg)
    }
}