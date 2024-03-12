package com.yuly.noinguin.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yuly.noinguin.fragment.MyAfterFragment
import com.yuly.noinguin.fragment.MyInformationFragment
import com.yuly.noinguin.fragment.MyResumeFragment

class MyPagerAdapter(fragmentManager:FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager,lifecycle) {

    val fragments = arrayOf(MyResumeFragment(), MyAfterFragment(), MyInformationFragment())

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}