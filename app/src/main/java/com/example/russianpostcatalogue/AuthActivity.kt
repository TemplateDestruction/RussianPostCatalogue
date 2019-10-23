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

    lateinit var spinnerCityDialog: SpinnerDialog
    lateinit var spinnerStreetDialog: SpinnerDialog
    lateinit var spinnerHomeDialog: SpinnerDialog


    private var cities = ArrayList<String>()
    private var streets = ArrayList<String>()
    private var homes = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_auth)
        val myDbHelper = DataBaseHelperus(this)
        initDataBase(myDbHelper)
        val dataBase = myDbHelper.writableDatabase
//        var cursor = dataBase.query(
//            "streets",
//            null,
//            "streets.name like '%" + "антон" + "%'",
//            null,
//            null,
//            null,
//            null
//        )
//        if (cursor.moveToFirst()) {
//            val indexName = cursor.getColumnIndex("name")
//            do {
//                Log.e("sql cursor: ", cursor.getString(indexName) + " | ")
//            } while (cursor.moveToNext())
//        }
        cities.addAll(resources.getStringArray(R.array.cities))
        spinnerCityDialog = SpinnerDialog(this, cities, "Выберите город", "Закрыть")
        spinnerCityDialog.setCancellable(true) // for cancellable
        spinnerCityDialog.setShowKeyboard(false) // for open keyboard by default
        spinnerCityDialog.bindOnSpinerListener { item, position ->
            fragAuth_city_text.text = item.toString()
//            val cursor = dataBase.query("streets", null, null, null, null, null, null, "50")
//            if (cursor.moveToFirst()) {
//                val index = cursor.getColumnIndex("name")
//                do {
//                    streets.add(cursor.getString(index))
//                } while (cursor.moveToNext())
//                println("streets:$streets")
//                spinnerStreetDialog.setItems(streets)
            fragAuth_street_layout.visibility = View.VISIBLE
//            } else {
//                Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show()
//            }
//            cursor.close()
        }
        fragAuth_city_layout.setOnClickListener {
            spinnerCityDialog.showSpinerDialog()
        }

        spinnerStreetDialog = SpinnerDialog(this, ArrayList<String>(10), "Выберите улицу", "Закрыть")
        spinnerStreetDialog.setCancellable(true) // for cancellable
        spinnerStreetDialog.setShowKeyboard(false) // for open keyboard by default
        spinnerStreetDialog.bindOnSpinerListener { item, position ->
            fragAuth_street_text.text = item.toString()
            var cursor = dataBase.query("homes", null, "code = ", null, null, null, null, "20")
            if (cursor.moveToFirst()) {
                val index = cursor.getColumnIndex("name")
                do {
                    homes.add(cursor.getString(index))
                } while (cursor.moveToNext())
                fragAuth_home_layout.visibility = View.VISIBLE
            } else {
                Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show()
            }
            cursor.close()
        }
        fragAuth_street_layout.setOnClickListener {
            spinnerStreetDialog.showSpinerDialog()
            rxBinds.addAll(
                RxTextView.afterTextChangeEvents(spinnerStreetDialog.searchBox).subscribe({
                    if (it.view().text.isNotEmpty()) {
                        var cursor = dataBase.query(
                            "streets",
                            null,
                            "streets.name like '%" + it.view().text.toString() + "%'",
                            null,
                            null,
                            null,
                            null
                        )
                        if (cursor.moveToFirst()) {
                            val indexName = cursor.getColumnIndex("name")
                            val array = ArrayList<String>()
                            do {
                                array.add(cursor.getString(indexName))
//                                Log.e("sql cursor: ", cursor.getString(indexName) + " | ")

                            } while (cursor.moveToNext())
//                            Log.e("win-win ", "gruzin")
                            spinnerStreetDialog.setItems(array)
                        }
                    }
                }, { it.fillInStackTrace() })
            )
        }
        spinnerHomeDialog = SpinnerDialog(this, homes, "Выберите дом", "Закрыть")
        spinnerHomeDialog.setCancellable(true) // for cancellable
        spinnerHomeDialog.setShowKeyboard(false) // for open keyboard by default
        spinnerHomeDialog.bindOnSpinerListener { item, position ->
            fragAuth_home_text.text = item.toString()
        }
        fragAuth_home_layout.setOnClickListener {
            spinnerHomeDialog.showSpinerDialog()
        }

        fragAuth_btnNext.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }


//        val streetPairs = mutableListOf<Pair<String, String>>()
//        val streets = resources.getStringArray(R.array.streets)
//        val codes = resources.getStringArray(R.array.codes)
//        for (i in streets.indices) {
//            streetPairs.add(Pair(streets[i], codes[i]))
//        }
        val cv = ContentValues()
        val db = DataBaseHelper(this).writableDatabase
//        db.delete("streets", null, null)
//        val stringBuilder = StringBuilder("INSERT INTO streets VALUES ")
//        for (streetPair in streetPairs) {
//            stringBuilder.append("(" + "'" + streetPai   r.first + "'" + ", " + "'" + streetPair.second + "'" + "), ")
//        }
//        stringBuilder.replace(stringBuilder.lastIndex - 1, stringBuilder.lastIndex, "")
//        db.execSQL(stringBuilder.toString())


//        val cursor = db.query("streets", null, null, null, null, null, null)
//        if (cursor.moveToLast()) {
////            cursor.moveToPosition(2000)
////            var i = 1000
//            val index = cursor.getColumnIndex("name")
//            val index2 = cursor.getColumnIndex("code")
//            do {
////                i--
//                Log.e("AuthAct", cursor.getString(index) + " | " + cursor.getString(index2))
//            } while (cursor.moveToNext())
//        } else Log.e("AuthAct", "FIASCO")

    }

    private fun initDataBase(myDbHelper: DataBaseHelperus) {
        try {
            myDbHelper.createDataBase()
        } catch (ioe: IOException) {
            throw Error("Unable to create database")
        }


        try {
            myDbHelper.openDataBase()
        } catch (sqle: SQLException) {
            throw sqle
        }
    }
}

class AuthActivityVm : BaseVm()
