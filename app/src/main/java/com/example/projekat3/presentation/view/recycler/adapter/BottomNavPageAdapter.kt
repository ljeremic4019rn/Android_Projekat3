package com.example.projekat3.presentation.view.recycler.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.projekat3.presentation.view.fragments.DiscoverFragment
import com.example.projekat3.presentation.view.fragments.PortfolioFragment
import com.example.projekat3.presentation.view.fragments.ProfileFragment


class BottomNavPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    companion object {
        const val ITEM_COUNT = 3
        const val FRAG_1_DISCOVER = 0
        const val FRAG_2_PORTFOLIO = 1
        const val FRAG_3_PROFILE = 2
    }

    override fun getCount(): Int {
        return ITEM_COUNT
    }

    override fun getItem(position: Int): Fragment {

        val fragment: Fragment = when (position) {
            FRAG_1_DISCOVER -> DiscoverFragment()
            FRAG_2_PORTFOLIO -> PortfolioFragment()
            FRAG_3_PROFILE -> ProfileFragment()
            else -> throw IllegalStateException("Unexpected value: $position")
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            FRAG_1_DISCOVER -> "Discover"
            FRAG_2_PORTFOLIO -> "Portfolio"
            FRAG_3_PROFILE -> "Profile"
            else -> "error"
        }
    }
}