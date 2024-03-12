package com.yuly.noinguin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.yuly.noinguin.R
import com.yuly.noinguin.databinding.ActivityLoginBinding

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
//        if (true) startActivity(Intent(this,MainActivity::class.java))
//        else AlertDialog.Builder(this).setMessage("이메일 또는 비밀번호가 일치하지 않습니다.")

    }
}