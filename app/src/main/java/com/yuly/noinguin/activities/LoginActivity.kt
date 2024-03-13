package com.yuly.noinguin.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.yuly.noinguin.R
import com.yuly.noinguin.data.LoginResponse
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




    }

    override fun onResume() {
        super.onResume()

        binding.btnLogin.setOnClickListener { clickLogin() }
    }

    private fun clickLogin(){
        //닷홈서버와 일치여부 확인
        val id = binding.inputLayoutId.editText!!.text.toString()
        val password = binding.inputLayoutPassword.editText!!.text.toString()

        val retrofit = RetrofitHelper.getRetrofitInstance()
        val retrofitService = retrofit.create(RetrofitService::class.java)
        retrofitService.loadUserAccountFromServer(id, password).enqueue(object :Callback <LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val loginResponse = response.body() //바디는 LoginResponse객체(rowNum과 account(UserAccountItem))으로 올거야.
                if (loginResponse?.rowNum!! > 0){
                   AlertDialog.Builder(this@LoginActivity).setMessage("${loginResponse.account.id}님 환영합니다^^").create().show()

                    //마법사 핸들러에게 별도쓰레드 위임받는 루퍼 준다
                    Handler(Looper.getMainLooper()).postDelayed({
                        val intent = Intent(this@LoginActivity,MainActivity::class.java)
                        intent.putExtra("id",loginResponse.account.id)
                            .putExtra("password",loginResponse.account.password)
                            .putExtra("imgFile", loginResponse.account.imgFile)
                        startActivity(intent)

                        finish()
                    },800)



                }else{
                    AlertDialog.Builder(this@LoginActivity).setMessage("아이디와 비밀번호가 일치하지 않습니다.\n다시 입력해주세요.").create().show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }


        })





    }
}