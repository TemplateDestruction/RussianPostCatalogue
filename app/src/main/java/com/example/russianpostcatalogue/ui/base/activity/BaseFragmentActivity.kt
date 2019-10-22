package xyz.tusion.nrboom_app.presentation.view.base.activity

import com.example.russianpostcatalogue.ui.base.activity.BaseVm

/**
 * Класс в котором реализуется общая логика для Activity в котором
 * меняются Fragments
 */
abstract class BaseFragmentActivity<VM : BaseVm> : BaseVmActivity<VM>()