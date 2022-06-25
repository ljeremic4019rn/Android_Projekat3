package com.example.projekat3.presentation.view.activities;

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.projekat3.R
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {


        val splashScreen: SplashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            val sharedPreferences = getSharedPreferences(packageName, MODE_PRIVATE)
            val username = sharedPreferences.getString("username", "")
            intent = if (username == "") {
                Intent(this, LoginActivity::class.java)
            } else {
                Intent(this, AppActivity::class.java)
            }
            startActivity(intent)
            finish()
            false
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}