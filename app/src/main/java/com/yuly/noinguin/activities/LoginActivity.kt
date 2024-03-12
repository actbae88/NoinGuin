package com.yuly.noinguin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.yuly.noinguin.R
import com.yuly.noinguin.data.UserAccountItem
import com.yuly.noinguin.databinding.ActivityLoginBinding
import com.yuly.noinguin.network.RetrofitHelper
import com.yuly.noinguin.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater)}




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //회원가입버튼 클릭 이벤트
        binding.btnSignup.setOnClickListener { startActivity(Intent(this, AgreementActivity::class.java)) }
        //둘러보기버튼 클릭 이벤트
        binding.btnBogi.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        //로그인버튼 클릭 이벤트 : 닷홈서버에 저장
        binding.btnLogin.setOnClickListener { clickLogin() }


    }

    private fun clickLogin(){
//        //닷홈서버와 일치여부 확인
        val id = binding.inputLayoutId.editText!!.text.toString()
        val password = binding.inputLayoutPassword.editText!!.text.toString()

        val retrofit = RetrofitHelper.getRetrofitInstance()
        val retrofitService = retrofit.create(RetrofitService::class.java)
        retrofitService.loadUserAccountFromServer(id, password).enqueue(object :Callback<UserAccountItem>{
            override fun onResponse(
                call: Call<UserAccountItem>,
                response: Response<UserAccountItem>
            ) {
                val items = response.body()



            }

            override fun onFailure(call: Call<UserAccountItem>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "실패: $t", Toast.LENGTH_SHORT).show()
            }

        })
//
//        val retrofit = RetrofitHelper.getRetrofitInstance()
//        val retrofitService = retrofit.create(RetrofitService::class.java)
//        retrofitService.loadUserAccountFromServer().enqueue(object :Callback<List<UserAccountItem>>{
//            override fun onResponse(call: Call<List<UserAccountItem>>, response: Response<List<UserAccountItem>>
//            ) {
//                itemList.clear()
//                val items = response.body()
//                items?.forEach {
//                    itemList.add(0,it)
//                }
//                binding.tvMyId.text = itemList[0].id
//                //이미지보여주기[DB에는 이미지경로가 "./upload/IMG_xxxx.jpg"로 되어있다.
//                //안드로이드에서는 서버의 전체주소가 필요하다.
//                val imgUrl = "http://baechu10.dothome.co.kr/06NoinGuin/${itemList[0].imgUri}"
//                Glide.with(requireContext()).load(imgUrl).into(binding.iv)
//
//
//            }
//
//            override fun onFailure(call: Call<List<UserAccountItem>>, t: Throwable) {
//                Toast.makeText(requireContext(), "실패$t", Toast.LENGTH_SHORT).show()
//            }
//
//        })
//
//
//
//
//    }


    }
}