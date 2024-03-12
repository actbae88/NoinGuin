package com.yuly.noinguin.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yuly.noinguin.fragment.JobListFragment
import com.yuly.noinguin.fragment.JobMapFragment


class JobPagerAdapter(fragmentManager: FragmentManager, lifecycle:Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragments = listOf<Fragment>(JobListFragment(), JobMapFragment())
    override fun getItemCount(): Int {
       return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}