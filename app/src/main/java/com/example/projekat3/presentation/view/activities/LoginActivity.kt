package com.example.projekat3.presentation.view.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projekat3.R

class LoginActivity : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var passwordInput: EditText
    private lateinit var registerButton: Button
    private lateinit var usernameInput: EditText
    private lateinit var emailInput: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
        initListeners()
    }

    private fun initView() {
        registerButton = findViewById(R.id.registerBtn)
        emailInput = findViewById(R.id.emailIn)
        loginButton = findViewById(R.id.loginBtn)
        usernameInput = findViewById(R.id.usernameIn)
        passwordInput = findViewById(R.id.passwordIn)
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

        val email = emailInput.text.toString()
        val username = usernameInput.text.toString()
        val password = passwordInput.text.toString()

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