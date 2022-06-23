package com.example.projekat3.presentation.view.activities;

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.projekat3.R
import com.example.projekat3.presentation.view.recycler.adapter.BottomNavPageAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class AppActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)

        initViewPager()
        initNavigation()
    }

    private fun initViewPager() {
        viewPager = findViewById(R.id.viewPager)
        viewPager.offscreenPageLimit = 3
        viewPager.adapter = BottomNavPageAdapter(supportFragmentManager)
    }

    private fun initNavigation() {
        (findViewById<View>(R.id.bottomNavigation) as BottomNavigationView).setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.navigation_1 -> viewPager.setCurrentItem(BottomNavPageAdapter.FRAG_1_DISCOVER, false)
                R.id.navigation_2 -> viewPager.setCurrentItem(BottomNavPageAdapter.FRAG_2_PORTFOLIO, false)
                R.id.navigation_3 -> viewPager.setCurrentItem(BottomNavPageAdapter.FRAG_3_PROFILE, false)
            }
            true
        }
    }

}