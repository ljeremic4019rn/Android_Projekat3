package com.example.projekat3.presentation.view.activities;

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projekat3.R

class LoginActivity : AppCompatActivity() {

    companion object {
        const val PASSWORD = "12345"
    }

    lateinit var usernameInput: EditText
    lateinit var passwordInput: EditText
    lateinit var emailInput: EditText
    lateinit var loginButton: Button

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
    }

    private fun initListeners() {
        val sharedPreferences = getSharedPreferences(packageName, MODE_PRIVATE)
        loginButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()
            val email = emailInput.text.toString()

            if (username != "" && password != "" && email != "" && password.length >= 5 && password == PASSWORD) {
                sharedPreferences
                    .edit()
                    .putString("username", username)
                    .putString("email", email)
                    .putString("password", password)
                    .apply()

                val intent = Intent(this, AppActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Error logging in", Toast.LENGTH_SHORT).show()
            }

        }
    }
}