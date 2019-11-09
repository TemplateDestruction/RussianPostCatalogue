package com.example.russianpostcatalogue

import android.content.ContentValues
import android.content.Intent
import android.database.Observable
import android.database.SQLException
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.Navigation
import com.example.russianpostcatalogue.domain.db.DataBaseHelper
import com.example.russianpostcatalogue.domain.spinerdialog.SpinnerDialog
import kotlinx.android.synthetic.main.fragment_auth.*
import kotlin.collections.ArrayList
import com.example.russianpostcatalogue.domain.db.DataBaseHelperus
import com.example.russianpostcatalogue.ui.base.activity.BaseVm
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxbinding2.widget.afterTextChangeEvents
import xyz.tusion.nrboom_app.presentation.view.base.activity.BaseActivity
import xyz.tusion.nrboom_app.presentation.view.base.activity.BaseFragmentActivity
import java.io.IOException


class AuthActivity : BaseFragmentActivity<AuthActivityVm>() {
    override fun getVmClass() = AuthActivityVm::class.java



    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.act_screener_nav_host_fragment).navigateUp()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_screener)



    }


}

class AuthActivityVm : BaseVm()
