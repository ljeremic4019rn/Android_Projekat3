package com.example.projekat3.presentation.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.projekat3.R
import com.example.projekat3.presentation.view.activities.LoginActivity

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    lateinit var usernameTextView: TextView
    lateinit var logoutButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view);
    }

    private fun init(view: View) {
        val sharedPreferences =
            activity?.getSharedPreferences(activity?.packageName, AppCompatActivity.MODE_PRIVATE)
        usernameTextView = view.findViewById(R.id.profileUsernameDisplay)

        val textToDisplay = sharedPreferences?.getString("username", "")

        usernameTextView.text = "Hello, $textToDisplay"
        logoutButton = view.findViewById(R.id.buttonLogOut)
        logoutButton.setOnClickListener {
            sharedPreferences?.edit()?.clear()?.apply()
            val loginIntent = Intent(activity, LoginActivity::class.java)
            startActivity(loginIntent)
            activity?.finish()
        }
    }
}