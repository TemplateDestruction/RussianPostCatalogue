package com.example.russianpostcatalogue.ui.auth

import android.content.Intent
import android.database.SQLException
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.russianpostcatalogue.MainActivity
import com.example.russianpostcatalogue.R
import com.example.russianpostcatalogue.domain.db.DataBaseHelperus
import com.example.russianpostcatalogue.domain.spinerdialog.SpinnerDialog
import com.example.russianpostcatalogue.ui.base.activity.BaseVm
import com.jakewharton.rxbinding2.widget.RxTextView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_auth.*
import kotlinx.android.synthetic.main.youtube_channel.view.*
import xyz.tusion.nrboom_app.presentation.view.base.fragment.BaseFragment
import xyz.tusion.nrboom_app.presentation.view.base.fragment.BaseVmFragment
import java.io.IOException

class AuthFragment : BaseFragment() {
    lateinit var spinnerDialog: SpinnerDialog

    lateinit var spinnerCityDialog: SpinnerDialog
    lateinit var spinnerStreetDialog: SpinnerDialog
    lateinit var spinnerHomeDialog: SpinnerDialog

//    lateinit var adapter: ChannelsAdapter

    private var cities = ArrayList<String>()
    private var streets = ArrayList<String>()
    private var streetsCodes = ArrayList<String>()
    private var homes = ArrayList<String>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_auth, container, false)
//        val textView: TextView = root.findViewById(R.id.text_feedback)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myDbHelper = DataBaseHelperus(requireContext())
        initDataBase(myDbHelper)
//        initRecyclerView()
        val dataBase = myDbHelper.writableDatabase



        cities.addAll(resources.getStringArray(R.array.cities))
        spinnerCityDialog = SpinnerDialog(requireActivity(), cities, "Выберите город", "Закрыть")
        spinnerCityDialog.setCancellable(true) // for cancellable
        spinnerCityDialog.setShowKeyboard(false) // for open keyboard by default
        spinnerCityDialog.bindOnSpinerListener { item, position ->
            fragAuth_city_text.text = item.toString()
            fragAuth_street_layout.visibility = View.VISIBLE
            spinnerCityDialog.closeSpinerDialog()
        }
        fragAuth_city_layout.setOnClickListener {
            spinnerCityDialog.showSpinerDialog()
        }

        spinnerStreetDialog =
            SpinnerDialog(requireActivity(), ArrayList<String>(), "Выберите улицу", "Закрыть")
        spinnerStreetDialog.setCancellable(true) // for cancellable
        spinnerStreetDialog.setShowKeyboard(false) // for open keyboard by default
//        spinnerStreetDialog.setUseContainsFilter(true)
        spinnerStreetDialog.bindOnSpinerListener { item, position ->
            fragAuth_street_text.text = item.toString()
            var cursor = dataBase.query(
                "homes",
                null,
                "homes.code LIKE '" + streetsCodes[position] + "%'",
                null,
                null,
                null,
                null
            )
            Log.e("Cursor ", streetsCodes.get(position))
            if (cursor.moveToFirst()) {
                val index = cursor.getColumnIndex("adress")
                homes.clear()
                do {
                    val str = cursor.getString(index)
                    homes.addAll(str.split(','))
                } while (cursor.moveToNext())
                fragAuth_home_layout.visibility = View.VISIBLE
            } else {
                Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show()
            }
            cursor.close()
            spinnerStreetDialog.closeSpinerDialog()
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
                            val indexCode = cursor.getColumnIndex("code")
                            streets.clear()
                            streetsCodes.clear()
                            do {
                                streets.add(cursor.getString(indexName))
                                streetsCodes.add(cursor.getString(indexCode))
                            } while (cursor.moveToNext())
                            spinnerStreetDialog.setItems(streets)
                        }
                    }
                }, { it.fillInStackTrace() })
            )
        }
        spinnerHomeDialog = SpinnerDialog(requireActivity(), homes, "Выберите дом", "Закрыть")
        spinnerHomeDialog.setCancellable(true) // for cancellable
        spinnerHomeDialog.setShowKeyboard(false) // for open keyboard by default
        spinnerHomeDialog.bindOnSpinerListener { item, position ->
            fragAuth_home_text.text = item.toString()
            password_edit_auth_frag.visibility = View.VISIBLE
            fragAuth_btnNext.visibility = View.VISIBLE
            spinnerHomeDialog.closeSpinerDialog()
        }
        fragAuth_home_layout.setOnClickListener {
            spinnerHomeDialog.showSpinerDialog()
        }

        fragAuth_btnNext.setOnClickListener {
            if (password_edit_auth_frag.text!!.isNotEmpty()) startActivity(Intent(requireContext(), MainActivity::class.java))
        }
    }

//    private fun initRecyclerView() {
//        adapter = ChannelsAdapter(streets)
//        fourth_question_rv.adapter = adapter
//
//    }

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




