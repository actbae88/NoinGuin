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
import retrofit2.http.Query

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



    }

    override fun onResume() {
        super.onResume()

        binding.btnLogin.setOnClickListener { clickLogin() }
    }

    private fun clickLogin(){
//        //닷홈서버와 일치여부 확인
        val id = binding.inputLayoutId.editText!!.text.toString()
        val password = binding.inputLayoutPassword.editText!!.text.toString()
        val retrofit = RetrofitHelper.getRetrofitInstance()
        val retrofitService = retrofit.create(RetrofitService::class.java)
        retrofitService.loadUserAccountFromServer(id, password).enqueue(object :Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                AlertDialog.Builder(this@LoginActivity).setMessage("성공: ${response.body()}").create().show()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                AlertDialog.Builder(this@LoginActivity).setMessage("실패:  ${t.message}").create().show()
            }

        })







    }
}