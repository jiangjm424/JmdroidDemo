package jm.droid.lib.uibase

import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ImmersionBar

abstract class BaseActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "BaseActivity"
        private const val DEBUG = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("onCreate:$this")
        ImmersionBar.with(this).statusBarColor("#ff0000").init()
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

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy:$this")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        log("onSaveInstanceState:$this")
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        log("onRestoreInstanceState:$this")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        log("onConfigurationChanged:$this")
    }

    private fun log(msg: String) {
        if (DEBUG) Log.i(TAG, msg)
    }
}