package com.yuly.noinguin.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yuly.noinguin.fragment.NewsDaumFragment
import com.yuly.noinguin.fragment.NewsNaverFragment

class NewsPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager,lifecycle) {

    private val fragments = listOf(NewsNaverFragment(), NewsDaumFragment())



    override fun getItemCount(): Int {
       return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}