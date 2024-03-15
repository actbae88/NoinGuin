package com.yuly.noinguin.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.core.view.size
import androidx.loader.content.CursorLoader
import com.bumptech.glide.Glide
import com.yuly.noinguin.R
import com.yuly.noinguin.data.LoginResponse
import com.yuly.noinguin.data.UserAccountItem
import com.yuly.noinguin.databinding.ActivitySignupBinding
import com.yuly.noinguin.network.RetrofitHelper
import com.yuly.noinguin.network.RetrofitService
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date

class SignupActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySignupBinding.inflate(layoutInflater) }
    private val items = arrayOf("선택안함","50대","60대","70대","80대")
    private var spinnerItem: String? =null
    lateinit var clickedFileName:String
    lateinit var id:String
    lateinit var password:String





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.btnTakePhoto.setOnClickListener { takePhoto() }
        binding.btnPickPhoto.setOnClickListener { pickPhoto() }
        binding.btnIdConfirm.setOnClickListener { idConfirm() }
        binding.btnOk.setOnClickListener { btnOk() }







        val myAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        binding.spinner.adapter= myAdapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(position){
                    0-> {spinnerItem = items[0]
                    //AlertDialog.Builder(this@SignupActivity).setMessage(spinnerItem).create().show()
                        }
                    1-> spinnerItem = items[1]
                    2-> spinnerItem = items[2]
                    3-> spinnerItem = items[3]
                    4-> spinnerItem = items[4]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@SignupActivity, "낫클릭", Toast.LENGTH_SHORT).show()
            }
        }

    }//oncreate




    var fileToImgUri:Uri? = null


    private fun takePhoto(){
        //사진찍기
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        //촬영한 이미지파일의 경로를 만들어주는 메소드
        setPhotoUri()
        fileToImgUri?.let {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, it)
        }
        //fileToImgUri가 절대경로이다. 그래서 따로 절대경로만들필요없음..
        //fileToImgUri--> content://abc/external_public/Pictures/IMG_20240312095750.jpg
        //왜 abc가 들어갔을까? 그리고 사진도 찍기전에 파일명이 정해지네? 즉 사진을 안찍어도 파일의경로가 만들어진다?
        AlertDialog.Builder(this).setMessage(clickedFileName).create().show()

        resultLauncher1.launch(intent)


    }



    val resultLauncher1=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode== RESULT_OK){
            Glide.with(this).load(fileToImgUri).into(binding.iv)
        }
    }


    lateinit var fileName:String
    private fun setPhotoUri(){
        //내장저장공간 중 외부저장소external 그 중 공용영역. 앱지워도 파일남아있음
        //공용영역의 경로와 파일이름 정하기
        val path:File = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val simpleDateFormat=SimpleDateFormat("yyyyMMddHHmmss")
        fileName = "IMG_" + simpleDateFormat.format(Date()) + ".jpg"
        val file:File = File(path,fileName)
        //Toast.makeText(this, "${file.toString()}", Toast.LENGTH_SHORT).show()
        //File경로를 콘텐츠경로인Uri로 변경하자( 프로바이더 등록!! )
        fileToImgUri = FileProvider.getUriForFile(this,"abc",file)
        clickedFileName = file.toString()
        //AlertDialog.Builder(this).setMessage(fileName).create().show()
    }




    //----------------------------------------------------------------------------

    private fun pickPhoto(){
        //사진가져오기
        val intent= if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.TIRAMISU) Intent(MediaStore.ACTION_PICK_IMAGES) else Intent(Intent.ACTION_OPEN_DOCUMENT).setType("image/*")
        resultLauncher2.launch(intent)


    }


    val resultLauncher2 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        val uri:Uri? = it.data?.data
        uri?.let {
            Glide.with(this).load(it).into(binding.iv)
            //uri를 절대경로로 바꿔야 서버에 저장가능!!
            imgRealPath2 = getRealPathFromUri2(uri)
            clickedFileName = imgRealPath2.toString()
        }
    }


    var imgRealPath2:String? = null

    private fun getRealPathFromUri2(uri: Uri) : String? {
        val cursorLoader:CursorLoader= CursorLoader(this,uri,null,null,null,null)
        val cursor:Cursor? =cursorLoader.loadInBackground()
        val fileName:String? = cursor?.run{
            moveToFirst()
            getString(getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
        }
        val file:File=File(externalCacheDir, fileName)
        val inputStream:InputStream = contentResolver.openInputStream(uri)?: return null
        val  outputStream:OutputStream = FileOutputStream(file)
        while (true){
            val buf:ByteArray= ByteArray(1024)
            val len:Int = inputStream.read(buf)
            if (len<=0) break

            outputStream.write(buf,0,len)
        }
        inputStream.close()
        outputStream.close()
        //AlertDialog.Builder(this).setMessage(file.absolutePath).create().show()
        return file.absolutePath
    }



//-------------------------------------------




    private fun idConfirm(){

        //서버에 아이디중복확인
        id = binding.inputLayoutId.editText!!.text.toString()

        val retrofitService = RetrofitHelper.getRetrofitInstance().create(RetrofitService::class.java)
        retrofitService.confirmIdFromServer(id).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.body()=="1"){
                    Toast.makeText(this@SignupActivity, "중복된 아이디가 있습니다ㅠㅠ\n새로운 아이디를 입력하세요.", Toast.LENGTH_SHORT).show()
                }else if (id == ""){
                    Toast.makeText(this@SignupActivity, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@SignupActivity, "사용 가능한 아이디입니다^^", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(this@SignupActivity, "$t", Toast.LENGTH_SHORT).show()
            }
        }) //아이디중복확인 레트로핏서비스


        
    }//idConfirm


    //-------------------------------------------


    private fun btnOk(){
        //서버에 저장 . 액티비티이동

        //1.사진 절대경로 clickedFileName

        //2.아이디
        id = binding.inputLayoutId.editText!!.text.toString()
        val retrofitService = RetrofitHelper.getRetrofitInstance().create(RetrofitService::class.java)
        retrofitService.confirmIdFromServer(id).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.body()=="1"){
                    Toast.makeText(this@SignupActivity, "중복된 아이디가 있습니다ㅠㅠ\n새로운 아이디를 입력하세요.", Toast.LENGTH_SHORT).show()
                }else{ //서버에 중복된 아이디가 없을때

                    if(id.equals("")){
                        Toast.makeText(this@SignupActivity, "아이디는 필수 입력사항 입니다.", Toast.LENGTH_SHORT).show()
                    }else id=binding.inputLayoutId.editText!!.text.toString()
                    //3.비밀번호 준비완료 - password
                    password = binding.inputLayoutPassword.editText!!.text.toString()
                    var passwordC= binding.inputLayoutPasswordConfirm.editText!!.text.toString()
                    if (password!=passwordC) {
                        Toast.makeText(this@SignupActivity, "비밀번호를 다시 확인해주세요. 서로 달라요", Toast.LENGTH_SHORT).show()
                    }else if (password.equals("")||passwordC.equals("")){
                        Toast.makeText(this@SignupActivity, "비밀번호는 필수 입력사항 입니다.", Toast.LENGTH_SHORT).show()
                    }
                    //4.나이대:준비완료 - spinnerItem

                    if (id.isNotEmpty() && password.equals(passwordC) && password!="" && passwordC!="" ){
                        //모든게 준비완료 됬으니 서버에 보내고, 폰환경설정에도 보내자.
                        Toast.makeText(this@SignupActivity, "회원가입을 축하드립니다^_^", Toast.LENGTH_SHORT).show()
                        sendPreferences()
                        sendServer()
                        startActivity(Intent(this@SignupActivity,LoginActivity::class.java))
                    }


                }
            }//서어베 중복된 아이디 없을때

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(this@SignupActivity, "$t", Toast.LENGTH_SHORT).show()
            }
        }) //아이디중복확인 레트로핏서비스





    }//btnOk method..


    private fun sendPreferences(){
        val preferences = getSharedPreferences("account", MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("id",id)
        editor.putString("password", password)
        editor.apply()
    }

    private fun sendServer(){

        val retrofit = RetrofitHelper.getRetrofitInstance()
        val retrofitService = retrofit.create(RetrofitService::class.java)

        val dataPart : MutableMap<String,String> = mutableMapOf()
            dataPart["id"] = id
            dataPart["password"] = password
            dataPart["age"] = spinnerItem as String


        val filePart : MultipartBody.Part? = clickedFileName.let {
            val file = File(it)
            val requestBody = RequestBody.create(MediaType.parse("image/*"),file)
            MultipartBody.Part.createFormData("img1",file.name,requestBody)
        }
        //이미지캡처 - /storage/emulated/0/Pictures/IMG_20240312150327.jpg

        retrofitService.postUserAccountToServer(dataPart,filePart).enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val preferencesEditor = getSharedPreferences("account",Context.MODE_PRIVATE).edit()
                val userAccount = response.body()
                preferencesEditor.putString("id",userAccount?.account?.id )
                preferencesEditor.putString("password",userAccount?.account?.password)
                preferencesEditor.putString("imgFile", userAccount?.account?.imgFile)
                preferencesEditor.apply()

                AlertDialog.Builder(this@SignupActivity).setMessage(clickedFileName).create().show()
//               startActivity(Intent(this@SignupActivity,LoginActivity::class.java))
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                AlertDialog.Builder(this@SignupActivity).setMessage("실패이유:$t").create().show()
            }

        })







    }

}