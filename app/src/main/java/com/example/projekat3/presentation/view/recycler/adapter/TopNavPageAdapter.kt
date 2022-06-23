package com.example.projekat3.presentation.view.recycler.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.projekat3.presentation.view.fragments.*

class TopNavPageAdapter (fm: FragmentManager) : FragmentPagerAdapter(fm){

    companion object {
        const val ITEM_COUNT = 2
        const val FRAG_1_NEWS = 0
        const val FRAG_2_POPULAR_STOCKS = 1
    }

    override fun getCount(): Int {
        return ITEM_COUNT
    }

    override fun getItem(position: Int): Fragment {
        val fragment: Fragment = when (position) {
            FRAG_1_NEWS -> NewsFragment()
            FRAG_2_POPULAR_STOCKS -> PopularStocksFragment()
            else -> throw IllegalStateException("Unexpected value: $position")
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            FRAG_1_NEWS -> "News"
            FRAG_2_POPULAR_STOCKS -> "Popular stocks"
            else -> "error"
        }
    }

}