package com.example.russianpostcatalogue.ui.base.activity

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * Базовый ViewModel класс, сюда заносить все, что общее для всех vm
 */
abstract class BaseVm : ViewModel() {

    /**
     * Все биндинги для BaseVm должны быть добавлены к этому CompositeDisposable,
     * он отвечает за работу с жизненым циклом ViewModel
     */
    protected val binds: CompositeDisposable = CompositeDisposable()

    /**
     * Здесь должны добавляться все необходимые биндинги к [binds]
     */
    open fun createVmBinds() {

    }

    override fun onCleared() {
        binds.clear()
        super.onCleared()
    }

}