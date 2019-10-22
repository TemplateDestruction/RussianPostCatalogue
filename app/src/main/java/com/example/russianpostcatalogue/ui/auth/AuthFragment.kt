package com.example.russianpostcatalogue.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.russianpostcatalogue.R
import com.example.russianpostcatalogue.domain.spinerdialog.SpinnerDialog
import kotlinx.android.synthetic.main.fragment_auth.*

class AuthFragment : Fragment() {
    lateinit var spinnerDialog: SpinnerDialog

    private var cities = ArrayList<String>()
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
        cities.addAll(resources.getStringArray(R.array.cities))
        spinnerDialog = SpinnerDialog(requireActivity(), cities, "Выберите город", "Закрыть")
        spinnerDialog.setCancellable(true) // for cancellable
        spinnerDialog.setShowKeyboard(false) // for open keyboard by default
        spinnerDialog.bindOnSpinerListener { item, position ->
            fragAuth_city_text.text = item.toString()
        }
        fragAuth_city_layout.setOnClickListener {
            spinnerDialog.showSpinerDialog()
        }
        fragAuth_btnNext.setOnClickListener {
            if (password_edit_auth_frag.text!!.isNotEmpty()) {
                findNavController().navigate(R.id.nav_home)
            }
        }
    }
}