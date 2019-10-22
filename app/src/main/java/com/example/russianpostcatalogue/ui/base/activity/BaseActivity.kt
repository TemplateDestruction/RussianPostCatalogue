package xyz.tusion.nrboom_app.presentation.view.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import io.reactivex.disposables.CompositeDisposable
import xyz.tusion.nrboom_app.presentation.view.base.fragment.OnBackPressedListener

/**
 * Содержит некоторую логику общую для всех Activity в проекте.
 */
abstract class BaseActivity : AppCompatActivity() {

    protected val rxBinds = CompositeDisposable()

    protected var firstStart = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        if (savedInstanceState != null) firstStart = false
    }

    override fun onResume() {
        super.onResume()
        if (firstStart) {
            init()
            firstStart = false
        }
        createBinds()
    }

    override fun onPause() {
        super.onPause()
        rxBinds.clear()
    }

    override fun onBackPressed() {
        for (fragment in supportFragmentManager.fragments)
            if (fragment is OnBackPressedListener && fragment.onBackPressed())
                return
        super.onBackPressed()
    }

    protected open fun createBinds() {
    }


    /**
     * Метод вызывается один раз при старте активити
     * Здесь можно обработать переданные в Intent данные
     */
    protected open fun init() {
    }
}