package com.example.projekat3.presentation.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.projekat3.R
import com.example.projekat3.presentation.view.recycler.adapter.TopNavPageAdapter
import com.google.android.material.tabs.TabLayout

class DiscoverFragment : Fragment(R.layout.fragment_discover){

    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNavigation(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    private fun initNavigation(view: View){
        viewPager = view.findViewById<View>(R.id.viewPagerTabs) as ViewPager
        viewPager.offscreenPageLimit = 10
        tabLayout = view.findViewById<View>(R.id.tabLayout) as TabLayout

        viewPager.adapter = TopNavPageAdapter(requireActivity().supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
    }
}