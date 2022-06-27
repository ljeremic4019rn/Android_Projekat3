package com.example.projekat3.presentation.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.projekat3.R
import com.example.projekat3.presentation.view.recycler.adapter.viewAdapters.TopNavPageAdapter
import com.google.android.material.tabs.TabLayout

class DiscoverFragment : Fragment(R.layout.fragment_discover) {

    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNavigation(view)
    }

    private fun initNavigation(view: View) {
        viewPager = view.findViewById<View>(R.id.viewPagerTabs) as ViewPager
        tabLayout = view.findViewById<View>(R.id.tabLayout) as TabLayout

        viewPager.adapter = TopNavPageAdapter(requireActivity().supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
    }
}