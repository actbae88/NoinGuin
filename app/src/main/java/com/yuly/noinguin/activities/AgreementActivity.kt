package com.yuly.noinguin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.yuly.noinguin.R
import com.yuly.noinguin.databinding.ActivityAgreementBinding

class AgreementActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAgreementBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnOk.setOnClickListener {
            if (binding.cb1.isChecked==true && binding.cb2.isChecked==true){
                startActivity(Intent(this,SignupActivity::class.java))
            }else AlertDialog.Builder(this).setMessage("모두 동의하셔야 회원가입이 가능합니다").create().show()
        }

        binding.toolbar.setNavigationOnClickListener { finish() }

    }
}