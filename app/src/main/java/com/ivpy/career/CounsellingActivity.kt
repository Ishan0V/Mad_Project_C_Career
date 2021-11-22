package com.ivpy.career

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

class CounsellingActivity : AppCompatActivity() {
    lateinit var navCrtl : NavController
    lateinit var navDraw :DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counselling)
        val navHostFragment= supportFragmentManager.findFragmentById(R.id.counsellingNavHost) as NavHostFragment
        navCrtl = navHostFragment.navController
    }
}