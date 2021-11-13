package com.ivpy.career

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashScreen : AppCompatActivity() {
    lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        auth=Firebase.auth
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            },1400)
        }else{
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            },1400)
        }
    }
}