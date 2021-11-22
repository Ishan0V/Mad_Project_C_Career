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


class AfterTenthFragment : Fragment() {




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_after_tenth, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val vView = view.findViewById<WebView>(R.id.TenthCounsel)
        vView.webViewClient= WebViewClient()
        vView.loadUrl("https://www.mindler.com/blog/how-to-choose-right-stream-class-10th/")
        vView.settings.javaScriptEnabled =true
        val mentorButton : Button = view.findViewById(R.id.MentorButton)
        mentorButton.setOnClickListener {
            val intent= Intent(context,MentorActivity::class.java)
            startActivity(intent)
        }
        super.onViewCreated(view, savedInstanceState)
    }
}