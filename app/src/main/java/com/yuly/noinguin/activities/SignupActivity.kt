package com.yuly.noinguin.activities

import android.content.Intent
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
import com.yuly.noinguin.databinding.ActivitySignupBinding
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date

class SignupActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySignupBinding.inflate(layoutInflater) }
    val items = arrayOf("선택안함","50대","60대","70대","80대")
    var spinnerItem: String? =null










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
    var imgRealPath1:String? = null


    private fun takePhoto(){
        //사진찍기
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        //촬영한 이미지파일의 경로를 만들어주는 메소드
        setPhotoUri()
        fileToImgUri?.let {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, it)
        }
        //AlertDialog.Builder(this).setMessage(fileToImgUri.toString()).create().show()
        resultLauncher1.launch(intent)

        //서버에 보내기 위해,img Uri를 절대경로로 바꾸기
        //오류남..ㅠㅠ imgRealPath1 =  getRealPathFromUri1(fileToImgUri!!)
    }


    private fun getRealPathFromUri1(fileToImgUri:Uri) : String? {
        val cursorLoader:CursorLoader= CursorLoader(this,fileToImgUri,null,null,null,null)
        val cursor:Cursor? =cursorLoader.loadInBackground()
        val fileName:String? = cursor?.run{
            moveToFirst()
            getString(getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
        }
        val file:File=File(externalCacheDir, fileName)
        val inputStream:InputStream = contentResolver.openInputStream(fileToImgUri)?: return null
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



    val resultLauncher1=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode== RESULT_OK){
            Glide.with(this).load(fileToImgUri).into(binding.iv)
        }
    }



    private fun setPhotoUri(){
        //내장저장공간 중 외부저장소external 그 중 공용영역. 앱지워도 파일남아있음
        //공용영역의 경로와 파일이름 정하기
        val path:File = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val simpleDateFormat=SimpleDateFormat("yyyyMMddHHmmss")
        val fileName:String = "IMG_" + simpleDateFormat.format(Date()) + ".jpg"
        val file:File = File(path,fileName)
        //Toast.makeText(this, "${file.toString()}", Toast.LENGTH_SHORT).show()
        //File경로를 콘텐츠경로인Uri로 변경하자( 프로바이더 등록!! )
        fileToImgUri = FileProvider.getUriForFile(this,"abc",file)
        AlertDialog.Builder(this).setMessage(fileToImgUri.toString()).create().show()
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
            //AlertDialog.Builder(this).setMessage("uri ---> $uri ")

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


    }


    //-------------------------------------------


    private fun btnOk(){
        //서버에 저장 . 액티비티이동

        //사진 절대경로, 아이디, 비밀번호, 나이대  모두String
        //1.사진 절대경로(이미지캡처한건지 픽한건지 알아야함.우선은..imgRealPath2만)

        //2.아이디
        val id = binding.inputLayoutId.editText!!.text.toString()
        //3.비밀번호 준비완료 - password
        if (binding.inputLayoutPassword==binding.inputLayoutPasswordConfirm){
            val password = binding.inputLayoutPassword.editText!!.text.toString()
        }else Toast.makeText(this, "비밀번호가 다릅니다. 다시 확인해주세요.", Toast.LENGTH_SHORT).show()
        //4.나이대:준비완료 - spinnerItem

        //예제 66번을 참고하여 jsom..하자..서버..
//        //포스트방식으로. 보드아이템객체를 전달하겠다. 포스트는 객체도전달해요^^ 그리고 응답받는것까지..
//        val retrofit = RetrofitHelper.getRetrofitInstance()
//        val retrofitService = retrofit.create(RetrofitService::class.java)
//
//        //서버로보낼 데이터를 가진 BoardItem객체 준비하겠다..
//        val item:BoardItem = BoardItem("배유리", "언니 짱이어요. 하하하하하하하핳하하하하하")
//       val call =  retrofitService.postMethodTest(item)
//        call.enqueue(object : Callback<BoardItem>{
//            override fun onResponse(call: Call<BoardItem>, response: Response<BoardItem>) {
//                val item= response.body()
//                item?.apply { binding.tv.text= "$name, ${msg}"}
//            }
//
//            override fun onFailure(call: Call<BoardItem>, t: Throwable) {
//                Toast.makeText(this@MainActivity, "실패했다 어쩌냐.ㅠㅠ", Toast.LENGTH_SHORT).show()
//            }

    }

}