package xyz.tusion.nrboom_app.presentation.view.base.fragment

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.russianpostcatalogue.ui.base.activity.BaseVm

/**
 * Базовый Fragment с [BaseVm] классом
 *
 * EN: Base Fragment with [BaseVm] class
 */
abstract class BaseVmFragment<VM : BaseVm> : BaseFragment() {

    /**
     * Переопределить если понадобится создать фабрику VM
     */
    protected open val vmFactory: ViewModelProvider.Factory? = null

    /**
     * ViewModel для Fragment
     */
    val vm: VM by lazy {
        if (vmFactory == null)
            ViewModelProviders
                .of(this)
                .get(getVmClass())
        else
            ViewModelProviders
                .of(this, vmFactory)
                .get(getVmClass())
    }

    /**
     * Метод для поддержки дженериков,
     * он нужен для инициализации VM (для [ViewModelProviders.of])
     */
    protected abstract fun getVmClass(): Class<VM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.createVmBinds()
    }
}