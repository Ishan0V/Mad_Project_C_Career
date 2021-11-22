package com.ivpy.career

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI


class InitialCounselFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_initial_counsel, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tenthButton : Button=view.findViewById(R.id.TenthStandardButton)
        val tewelthButton : Button =view.findViewById(R.id.TewelthStandard)
        tenthButton.setOnClickListener {
            findNavController().navigate(R.id.action_initialCounselFragment_to_afterTenthFragment)
        }
        tewelthButton.setOnClickListener {
            findNavController().navigate(R.id.action_initialCounselFragment_to_tewelthFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }
}