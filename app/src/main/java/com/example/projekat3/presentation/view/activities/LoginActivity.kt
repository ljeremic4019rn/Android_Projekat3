package com.example.projekat3.presentation.view.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projekat3.R

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
        initListeners()
    }

    private fun init() {
        usernameInput = findViewById(R.id.usernameInput)
        passwordInput = findViewById(R.id.passwordInput)
        emailInput = findViewById(R.id.emailInput)
        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)
    }

    /*
    mode je da bi se znalo da li da se stavi novi user na bazu ili da se ucita stari
    ako se uloguje kao ne postojeci user puca program !!!
     */

    private fun initListeners() {
        loginButton.setOnClickListener {
            saveInfo("LOGIN")
        }

        registerButton.setOnClickListener{
            saveInfo("REGISTER")
        }
    }

    private fun saveInfo(mode: String){
        val sharedPreferences = getSharedPreferences(packageName, MODE_PRIVATE)

        val username = usernameInput.text.toString()
        val password = passwordInput.text.toString()
        val email = emailInput.text.toString()

        if (username != "" && password != "" && email != "" && password.length >= 5) {
            sharedPreferences
                .edit()
                .putString("mode", mode)
                .putString("username", username)
                .putString("email", email)
                .putString("password", password)
                .apply()

            val intent = Intent(this, AppActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Error logging in, pass > 5", Toast.LENGTH_SHORT).show()
        }
    }
}