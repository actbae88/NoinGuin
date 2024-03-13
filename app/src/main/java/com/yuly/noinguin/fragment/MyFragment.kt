package com.yuly.noinguin.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.yuly.noinguin.R
import com.yuly.noinguin.adapter.MyPagerAdapter
import com.yuly.noinguin.databinding.FragmentMyBinding


class MyFragment : Fragment() {

    lateinit var binding:FragmentMyBinding
    lateinit var id:String
    lateinit var password:String
    lateinit var imgFile:String



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMyBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





        binding.pager.adapter = MyPagerAdapter(childFragmentManager ,lifecycle)
        val tabTitle = arrayOf("이력서", "면접 후기", "내 정보")
        val tabIcon= arrayOf(R.drawable.my_resume, R.drawable.my_after, R.drawable.my_account)
        binding.tabLayout.tabIconTint = null

        TabLayoutMediator(binding.tabLayout,binding.pager,object : TabLayoutMediator.TabConfigurationStrategy{
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                tab.text = tabTitle[position]
                tab.setIcon(tabIcon[position])
            }
        }).attach()



        getUserAccount()


    }//온크리





    //메인액티에서 보낸 번들꾸러미의 아규먼트들 꺼내보장
    private fun getUserAccount(){
        id = arguments?.getString("id").toString()
        password = arguments?.getString("password").toString()
        imgFile = arguments?.getString("imgFile").toString()

        AlertDialog.Builder(requireContext()).setMessage(id+password+imgFile).create().show()


    }


}