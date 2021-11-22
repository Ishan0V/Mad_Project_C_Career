package com.ivpy.career

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button


class MScienceFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_m_science, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val vView = view.findViewById<WebView>(R.id.mscienceWebview)
        vView.webViewClient= WebViewClient()
        vView.loadUrl("https://www.edumilestones.com/blog/details/best-career-options-for-PCB-biology-except-medical-after-12th")
        vView.settings.javaScriptEnabled =true
        val mentorButton : Button= view.findViewById(R.id.MentorMscience)
        mentorButton.setOnClickListener {
            val intent=Intent(context,MentorActivity::class.java)
            startActivity(intent)
        }
        super.onViewCreated(view, savedInstanceState)
    }

}