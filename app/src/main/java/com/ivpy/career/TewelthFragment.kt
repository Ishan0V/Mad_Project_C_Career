package com.ivpy.career

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.navigation.fragment.findNavController


class TewelthFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tewelth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val pcbButton :Button = view.findViewById(R.id.pcbButton)
        pcbButton.setOnClickListener {
            findNavController().navigate(R.id.action_tewelthFragment_to_MScienceFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }

}