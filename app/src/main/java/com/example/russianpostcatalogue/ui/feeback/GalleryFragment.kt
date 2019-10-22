package com.example.russianpostcatalogue.ui.feeback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.russianpostcatalogue.R

class GalleryFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_feedback, container, false)
//        val textView: TextView = root.findViewById(R.id.text_feedback)
        return root
    }
}