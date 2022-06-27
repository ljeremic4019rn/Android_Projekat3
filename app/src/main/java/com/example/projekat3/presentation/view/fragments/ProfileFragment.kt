package com.example.projekat3.presentation.view.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.projekat3.R
import com.example.projekat3.presentation.view.activities.LoginActivity

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var usernameTv: TextView
    private lateinit var button: Button
    private lateinit var sharedPreferences: SharedPreferences


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = activity?.getSharedPreferences(activity?.packageName, AppCompatActivity.MODE_PRIVATE)!!

        initView(view)
        initListeners()
    }

    private fun initView(view: View){
        usernameTv = view.findViewById(R.id.profileUsername)
        button = view.findViewById(R.id.logoutBtn)

        usernameTv.text = sharedPreferences.getString("username", "")
    }

    private fun initListeners() {
        button.setOnClickListener {
            sharedPreferences.edit()?.clear()?.apply()
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }
    }
}