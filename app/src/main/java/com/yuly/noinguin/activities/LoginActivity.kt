package com.yuly.noinguin.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.yuly.noinguin.network.G
import com.yuly.noinguin.network.RetrofitHelper
import com.yuly.noinguin.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query

class LoginActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater)}
    lateinit var preferences:SharedPreferences
    lateinit var id:String
    lateinit var password:String
    lateinit var imgFile:String





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        existPreference()




    }//온크리




    override fun onResume() {
        super.onResume()

        binding.btnLogin.setOnClickListener { clickLogin() }
    }//온리쥼



    private fun existPreference(){
        //만약 Preference에 "account"-id,paasword가 있으면 걍 바로 메인고고싱 . 없으면 머물러라
        preferences = getSharedPreferences("account", MODE_PRIVATE)

        if (preferences.contains("id")){

            //프리퍼런스에서 id. password가꼬와.
            id  = preferences.getString("id","디폴트아이디").toString()
            password = preferences.getString("password", "디폴트패스워드").toString()
            //이미지는 서버에서 가꼬와.
            val retrofitService = RetrofitHelper.getRetrofitInstance().create(RetrofitService::class.java)
            retrofitService.loadUserAccountFromServer(id,password).enqueue(object : Callback<LoginResponse>{
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    imgFile = response.body()?.account?.imgFile.toString()
                    preferences.edit().putString("imgFile",imgFile).apply()
                }
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "$t", Toast.LENGTH_SHORT).show()
                }
            })
            imgFile = preferences.getString("imgFile","디폴트이미지파일").toString()
            //G의 userAccountItem에 새로운객체만들어서 값넣주자 ^_____^
            G.userAccountItem = UserAccountItem(0, id, password, "", imgFile)






            Toast.makeText(this, "$id 님 반가워요^___^", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this,MainActivity::class.java))

        }else{
            //회원가입버튼 클릭 이벤트
            binding.btnSignup.setOnClickListener { startActivity(Intent(this, AgreementActivity::class.java)) }
            //둘러보기버튼 클릭 이벤트
            binding.btnBogi.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }

        }



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

                        preferences.edit().putString("id",loginResponse.account.id)
                        preferences.edit().putString("password", loginResponse.account.password)
                        preferences.edit().putString("imgFile", loginResponse.account.imgFile)

                        G.userAccountItem?.id = loginResponse.account.id
                        G.userAccountItem?.password = loginResponse.account.password
                        G.userAccountItem?.imgFile = loginResponse.account.imgFile

                        startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                        finish()

                    },800)



                }else{
                    AlertDialog.Builder(this@LoginActivity).setMessage("아이디와 비밀번호가 일치하지 않습니다.\n다시 입력해주세요.").create().show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }


        })//서버에서 사용자계정 가져오기





    }//로그인클릭했을때
}