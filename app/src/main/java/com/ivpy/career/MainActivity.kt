package com.ivpy.career

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.ivpy.career.databinding.ActivityMainBinding
import java.util.*
import kotlin.concurrent.timerTask

class MainActivity : AppCompatActivity() {
    lateinit var progressBar : Dialog
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val navDraw: DrawerLayout= binding.navMenuMain
        val navView:NavigationView=binding.mainMenuSlide
        toggle= ActionBarDrawerToggle(this,navDraw,R.string.open,R.string.close)
        navDraw.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.about-> startActivity(Intent(this@MainActivity,AboutActivity::class.java))
                R.id.slide_mentor ->startActivity(Intent(this@MainActivity,MentorActivity::class.java))
                R.id.slide_quick -> startActivity(Intent(this@MainActivity,CounsellingActivity::class.java))
                R.id.log_out_slide_menu ->logOut()
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return  true
        }
        return super.onOptionsItemSelected(item)
    }

    fun logOut(){
        showProgress()
        FirebaseAuth.getInstance().signOut()
        Toast.makeText(applicationContext,"Logged Out!!",Toast.LENGTH_SHORT).show()
        Timer().schedule(timerTask {
            hideProgress()
            finish()
            startActivity(Intent(this@MainActivity,LoginActivity::class.java))
        },500)
    }

    private fun showProgress(){
        progressBar = Dialog(this)
        progressBar.setContentView(R.layout.progress_fullscreen)
        progressBar.setCancelable(false)
        progressBar.setCanceledOnTouchOutside(false)
        progressBar.show()
    }
    private fun hideProgress(){
        progressBar.dismiss()
    }

}