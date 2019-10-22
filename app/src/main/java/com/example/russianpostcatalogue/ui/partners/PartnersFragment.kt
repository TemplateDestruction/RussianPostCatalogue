package com.example.russianpostcatalogue.ui.partners

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.russianpostcatalogue.R

class PartnersFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_tools, container, false)
        val textView: TextView = root.findViewById(R.id.text_tools)
        return root
    }
}