package com.yuly.noinguin.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.yuly.noinguin.R
import com.yuly.noinguin.adapter.JobPagerAdapter
import com.yuly.noinguin.databinding.FragmentJobBinding


class JobFragment : Fragment() {

    private val binding by lazy { FragmentJobBinding.inflate(layoutInflater) }






    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = JobPagerAdapter(childFragmentManager, lifecycle)
        binding.pager.adapter = adapter
        val tabTitle = arrayOf("일자리 목록", "일자리 지도")
        val tabIcon = arrayOf(R.drawable.job_list,R.drawable.job_map)


        TabLayoutMediator(binding.tabLayout,binding.pager, object : TabLayoutMediator.TabConfigurationStrategy{
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                tab.text = tabTitle[position]
                tab.setIcon(tabIcon[position])
                binding.tabLayout.tabIconTint = null
            }
        } ).attach()
    }


}