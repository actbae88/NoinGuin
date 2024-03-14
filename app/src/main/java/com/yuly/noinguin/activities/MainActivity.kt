package com.yuly.noinguin.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.yuly.noinguin.R
import com.yuly.noinguin.databinding.ActivityMainBinding
import com.yuly.noinguin.fragment.JjimJobFragment
import com.yuly.noinguin.fragment.JobFragment
import com.yuly.noinguin.fragment.MyFragment
import com.yuly.noinguin.fragment.NewsFragment
import com.yuly.noinguin.network.G

class MainActivity : AppCompatActivity(){

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var fragments:ArrayList<Fragment>
    private var id: String? = null
    private var password: String? = null
    private var imgFile: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //버텀네비게이션 아이템 색 보이게하는 코드
        binding.bnv.itemIconTintList = null

        id= G.userAccountItem?.id
        password = G.userAccountItem?.password
        imgFile = G.userAccountItem?.imgFile
        AlertDialog.Builder(this).setMessage(id+password+imgFile).create().show()

        fragments = arrayListOf()
        fragments.add(0, NewsFragment())
        fragments.add(1, JobFragment())
        fragments.add(2, JjimJobFragment())
        fragments.add(3, MyFragment())





        supportFragmentManager.beginTransaction().replace(R.id.container,fragments[0]).commit()

        binding.bnv.setOnItemSelectedListener {
           when(it.itemId){
               R.id.bnv_icon_news-> {
                   replaceFragment(0)
                   true
               }
               R.id.bnv_icon_job-> {
                   replaceFragment(1)
                   true
               }
               R.id.bnv_icon_jjim-> {
                   replaceFragment(2)
                    true
               }
               R.id.bnv_icon_my-> {
                   replaceFragment(3)
                   true
               }
                else -> false
           }
        }//버텀네비게이션뷰 클릭이벤트--> 프레그먼트 비긴트렌젝션
        




    }//온크리











    //서포트프레그먼트 매니저한테 트랜젝션시키고 리플레이스시키기
    private fun replaceFragment(no:Int){
        val fragment= fragments[no]
        supportFragmentManager.beginTransaction().replace(R.id.container,fragment).commit()
    }












}