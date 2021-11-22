package com.ivpy.career

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.ivpy.career.firestore.FirestoreClass
import com.ivpy.career.model.User
import java.util.*
import kotlin.concurrent.timerTask

class LoginActivity : AppCompatActivity() {
    lateinit var progressBar : Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val regClick : TextView = findViewById(R.id.register)
        val loginButton:Button =findViewById(R.id.login_button)
        regClick.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        loginButton.setOnClickListener{
            loginUser()
        }
    }

    private fun validateLogin():Boolean{
        val logEmail:String=findViewById<TextInputLayout>(R.id.login_email).editText!!.text.toString().trim()
        val logPass:String=findViewById<TextInputLayout>(R.id.login_pass).editText!!.text.toString().trim()

        return if(logEmail.isEmpty() || logPass.isEmpty()){
            Toast.makeText(applicationContext,"Enter all details",Toast.LENGTH_SHORT).show()
            false
        }else if(logEmail.indexOf('@',0)==-1 || logEmail.indexOf('.',0)==-1){
            Toast.makeText(applicationContext,"Enter a correct email",Toast.LENGTH_SHORT).show()
            true
        }else{
            true
        }
    }

    private fun loginUser(){
        showProgress()
        if(validateLogin()){
            val logEmail:String=findViewById<TextInputLayout>(R.id.login_email).editText!!.text.toString().trim()
            val logPass:String=findViewById<TextInputLayout>(R.id.login_pass).editText!!.text.toString().trim()
            FirebaseAuth.getInstance().signInWithEmailAndPassword(logEmail,logPass)
                .addOnCompleteListener { task->
                    if(task.isSuccessful){
                        FirestoreClass().getUserDetail(this@LoginActivity)
                    }
                    else{
                        hideProgress()
                        Toast.makeText(applicationContext,"Error in logging in",Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    fun showProgress(){
        progressBar = Dialog(this)
        progressBar.setContentView(R.layout.progress_fullscreen)
        progressBar.setCancelable(false)
        progressBar.setCanceledOnTouchOutside(false)
        progressBar.show()
    }
    fun hideProgress(){
        progressBar.dismiss()
    }

    fun userLoggedinSucces (user:User){
        hideProgress()
        startActivity(Intent(this@LoginActivity,CounsellingActivity::class.java))
        finish()
    }
}