package com.yuly.noinguin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.yuly.noinguin.R
import com.yuly.noinguin.databinding.ActivityMainBinding
import com.yuly.noinguin.fragment.JjimJobFragment
import com.yuly.noinguin.fragment.JobFragment
import com.yuly.noinguin.fragment.MyFragment
import com.yuly.noinguin.fragment.NewsFragment

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var fragments:ArrayList<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //버텀네비게이션 아이템 색 보이게하는 코드
        binding.bnv.itemIconTintList = null

        fragments = arrayListOf()
        fragments.add(0, NewsFragment())
        fragments.add(1, JobFragment())
        fragments.add(2, JjimJobFragment())
        fragments.add(3, MyFragment())


        supportFragmentManager.beginTransaction().replace(R.id.container,fragments[0]).commit()

        binding.bnv.setOnItemSelectedListener {
           when(it.itemId){
               R.id.bnv_icon_news-> {
                   this.supportFragmentManager.beginTransaction().replace(R.id.container,fragments[0]).commit()
                   true
               }
               R.id.bnv_icon_job-> {
                   this.supportFragmentManager.beginTransaction().replace(R.id.container,fragments[1]).commit()
                   true
               }
               R.id.bnv_icon_jjim-> {
                   supportFragmentManager.beginTransaction().replace(R.id.container,fragments[2]).commit()
                    true
               }
               R.id.bnv_icon_my-> {
                   supportFragmentManager.beginTransaction().replace(R.id.container,fragments[3]).commit()
                   true
               }
                else -> false
           }
        }




    }





}