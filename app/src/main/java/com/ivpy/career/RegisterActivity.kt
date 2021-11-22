package com.ivpy.career

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.ivpy.career.databinding.ActivityRegisterBinding
import com.ivpy.career.firestore.FirestoreClass
import com.ivpy.career.model.User
import java.util.*
import kotlin.concurrent.timerTask

class RegisterActivity : AppCompatActivity() {
    lateinit var progressBar : Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val loginReg:TextView= findViewById(R.id.login_reg)
        loginReg.setOnClickListener {
            val intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        val regButton:Button=findViewById(R.id.reg_button)
        regButton.setOnClickListener{
            registerUser()
        }
    }

    private fun validateDetail():Boolean{
        val fname:String=
            findViewById<TextInputLayout>(R.id.first_name_reg).editText!!.text.toString().trim()
        val lname:String=findViewById<TextInputLayout>(R.id.last_name_reg).editText!!.text.toString().trim()
        val pass:String=findViewById<TextInputLayout>(R.id.reg_pass).editText!!.text.toString().trim()
        val retypePass:String=findViewById<TextInputLayout>(R.id.retype_pass_reg).editText!!.text.toString().trim()
        val email:String=findViewById<TextInputLayout>(R.id.reg_email).editText!!.text.toString().trim()

        if(fname.isEmpty() || lname.isEmpty() || pass.isEmpty() || retypePass.isEmpty() || email.isEmpty()){
            Toast.makeText(applicationContext,"Enter all Details",Toast.LENGTH_SHORT).show()
            hideProgress()
            return false
        }else if(pass!=retypePass) {
            Toast.makeText(applicationContext,"Password does not match in both column",Toast.LENGTH_SHORT).show()
            hideProgress()
            return false
        }else if(email.indexOf('@',0)==-1 || email.indexOf('.',0)==-1){
            Toast.makeText(applicationContext,"Enter correct email",Toast.LENGTH_SHORT).show()
            hideProgress()
            return false
        }
        return true
    }

    private fun registerUser():Boolean{
        showProgress()
        var flag=false
        val email:String=findViewById<TextInputLayout>(R.id.reg_email).editText!!.text.toString().trim()
        val pass:String=findViewById<TextInputLayout>(R.id.reg_pass).editText!!.text.toString().trim()
        val lname:String=findViewById<TextInputLayout>(R.id.last_name_reg).editText!!.text.toString().trim()
        val fname:String=
            findViewById<TextInputLayout>(R.id.first_name_reg).editText!!.text.toString().trim()
        if(validateDetail()){
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this){ task->
                    if(task.isSuccessful){
                        flag=true
                        val firebaseUser:FirebaseUser=task.result!!.user!!
                        val user =User(
                            firebaseUser.uid,
                            fname,
                            lname,
                            email
                        )
                        FirestoreClass().registerUser(this@RegisterActivity,user)
                        /*FirebaseAuth.getInstance().signOut()

                        Timer().schedule(timerTask {
                            finish()
                        },100)*/
                    }else{
                        hideProgress()
                        Toast.makeText(applicationContext,"Error in registering",Toast.LENGTH_SHORT).show()
                    }
                }
        }
        return flag
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

    fun registerSuccess(){
        hideProgress()
        Toast.makeText(applicationContext,"Registered !!",Toast.LENGTH_SHORT).show()
    }

    /*override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }*/
}