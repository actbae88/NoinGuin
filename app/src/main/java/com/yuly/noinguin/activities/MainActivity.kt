package com.yuly.noinguin.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.yuly.noinguin.R
import com.yuly.noinguin.databinding.ActivityMainBinding
import com.yuly.noinguin.fragment.JjimJobFragment
import com.yuly.noinguin.fragment.JobFragment
import com.yuly.noinguin.fragment.MyFragment
import com.yuly.noinguin.fragment.NewsFragment

class MainActivity : AppCompatActivity(){

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var fragments:ArrayList<Fragment>
    private var id:String? = null
    private var password:String? = null
    private var imgFile:String? = null



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
        


        clickSabve() //로그인한 사용자정보를 쉐어드프레퍼런스로 저장하기

        load()




    }//온크리




    private fun load(){
        //Data.xml문서를 읽어주는 쉐어드프리퍼런스객체소환
        val preferences:SharedPreferences = getSharedPreferences("Data", MODE_PRIVATE)
        //모드프라이빗은 설정값을 쌓아두지않는데. 비번바꿀때 쌓아두지않는다.

//        다른 프레그먼트에서도 그냥 하면되는가.?
//        //자료형별로 데이터 가져오기
//        var name:String? = preferences.getString("name","")
//        var age:Int = preferences.getInt("age", 0)
//        var sound:Boolean = preferences.getBoolean("sound", true)
//
//        tv.text= "이름 : $name   나이 : $age   사운드 :  $sound"

    }


    //내부저장소에 환경설정공유하기.
    private fun clickSabve(){
        id = Intent().getStringExtra("id")
        password = Intent().getStringExtra("password")
        imgFile = Intent().getStringExtra("imgFile")
        //내부저장소 internal에 저장된다.
        val preferences:SharedPreferences = getSharedPreferences("Data", MODE_PRIVATE)
        val editor:Editor = preferences.edit()
        editor.putString("id", id)
        editor.putString("password", password)
        editor.putString("imgFile", imgFile)
        editor.apply() //commit은 동기, apply는 비동기. 넌너대로난나대로


    }



    //서포트프레그먼트 매니저한테 트랜젝션시키고 리플레이스시키기
    private fun replaceFragment(no:Int){
        val fragment= fragments[no]
        supportFragmentManager.beginTransaction().replace(R.id.container,fragment).commit()
    }












}