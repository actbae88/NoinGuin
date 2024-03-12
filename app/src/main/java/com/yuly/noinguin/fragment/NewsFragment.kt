package com.yuly.noinguin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.yuly.noinguin.R
import com.yuly.noinguin.adapter.NewsPagerAdapter
import com.yuly.noinguin.databinding.FragmentNewsBinding


class NewsFragment : Fragment() {

    private val binding by lazy { FragmentNewsBinding.inflate(layoutInflater) }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = NewsPagerAdapter(childFragmentManager,lifecycle)
        binding.pager.adapter = adapter
        val tabTitle = arrayOf( "Naver 뉴스", "Daum 뉴스")
        val tabIcons = arrayOf(R.drawable.news_naver, R.drawable.logo1_daum)

        TabLayoutMediator(binding.tabLayout,binding.pager,
            object : TabLayoutMediator.TabConfigurationStrategy {
                override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                    tab.text = tabTitle[position]
                    tab.setIcon(tabIcons[position])
                    binding.tabLayout.tabIconTint = null
                }
            }).attach()

    }
}