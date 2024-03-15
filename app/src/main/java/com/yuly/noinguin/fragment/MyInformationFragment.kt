package com.yuly.noinguin.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.loader.content.CursorLoader
import com.bumptech.glide.Glide
import com.yuly.noinguin.data.MyInfomationDataChange
import com.yuly.noinguin.databinding.FragmentMyInformationBinding
import com.yuly.noinguin.network.G
import com.yuly.noinguin.network.RetrofitHelper
import com.yuly.noinguin.network.RetrofitService
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream


class MyInformationFragment : Fragment() {

    val binding by lazy { FragmentMyInformationBinding.inflate(layoutInflater) }
    lateinit var id:String
    lateinit var password:String
    lateinit var imgFile:String
    private var isImageChanged:Boolean = false
    private var pwPerfect:Boolean = false




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        id = G.userAccountItem?.id.toString()
        password = G.userAccountItem?.password.toString()
        imgFile = G.userAccountItem?.imgFile.toString()

        binding.tvMyId.text = id//기본id보여주기
        //binding.tvMyId.text = imgFile : ./signupimg/IMG_2024~~.jpg

        //기본 이미지 보여주기
        val imgUrl = "http://baechu10.dothome.co.kr/06NoinGuin/${G.userAccountItem?.imgFile}"
        //주소가올바른지 확인해보자
        Log.d("img", "주소알려줘~~~~~ : $imgFile")
        Glide.with(requireContext()).load(imgUrl).into(binding.iv)

        //사진변경 버튼 클릭 이벤트처리
        binding.btnIvChange.setOnClickListener { changePhoto() }
        binding.btnPwOk.setOnClickListener{ confirmPassword(pwPerfect) }

        binding.btnOk.setOnClickListener { clickSaveBtn() }


    }//온뷰크리에이티드



    private fun changePhoto(){
        val preferences = activity?.getSharedPreferences("account",Context.MODE_PRIVATE)
        if (preferences?.contains("id")==true){//프리퍼런스에 아이디있어?
            val intent = if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU) Intent(MediaStore.ACTION_PICK_IMAGES) else Intent(Intent.ACTION_OPEN_DOCUMENT).setType("image/*")
            //AlertDialog.Builder(requireContext()).setMessage(imgFile).create().show()
            resultLauncher.launch(intent)
        }else{
            Toast.makeText(requireContext(), "회원가입 또는 로그인하셔야 사진변경이 가능합니다.", Toast.LENGTH_SHORT).show()
        }

        //저장버튼 클릭시, 바뀐 imgRealPath를 서버로 UPDATE 잊지말자^^

    }//changePhoto()



    //멤버변수에..부릉이 리절트런처
    val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.data?.data!=null){ //인텐트가 가져온 사진데이터가 있으면..
            val uri: Uri? = it.data?.data
            uri?.let {
                Glide.with(requireContext()).load(uri).into(binding.iv)
                //binding.tv.text = uri.toString() -- content://media/picker/0/com/android/providers.media...절대경로로 바꿔야함!!
                imgRealPath = getRealPathFromUri(uri)
                //binding.tv.text = imgRealPath --/storage/emulated/0/Android/data/com/yuly.noinguin/cache/1000..jpg
                isImageChanged = true //이미지 바꼈어!!
            }
        }else{//인텐트가 액션픽이미지는했는데 사용자가 암껏도선택안하고 되돌아오면
            it.data?.data = null
            isImageChanged = false //이미지 안바꼈어!!
        }
    }//리절트런처


    //멤버변수에 절대경로변수
    var imgRealPath:String? = null


    //절대경로로 변화작업 메소드
    private fun getRealPathFromUri(uri: Uri) : String? {
        val cursorLoader:CursorLoader = CursorLoader(requireContext(),uri,null,null,null,null)
        val cursor:Cursor? = cursorLoader.loadInBackground()
        val fileName:String? = cursor?.run {
            moveToFirst()
            getString(getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
        }
        val file:File= File(activity?.externalCacheDir, fileName)
        val inputStream:InputStream = activity?.contentResolver?.openInputStream(uri)?: return null
        val outputStream:OutputStream = FileOutputStream(file)
        while (true){
            val buf:ByteArray= ByteArray(1024)
            val len:Int= inputStream.read(buf)
            if (len<=0) break

            outputStream.write(buf,0,len)
        }
        inputStream.close()
        outputStream.close()
        return file.absolutePath

    }//getReaPathFromUri()





    //------------------------------------------------

    //pw확인 버튼클릭시 --비밀번호 컨펌해줘도 되겠닝? 확인해보장!!
    private fun confirmPassword(pwPerfect: Boolean) : Boolean{
        val etPassword = binding.inputLayoutPassword.editText!!.text.toString()
        val etPasswordC = binding.inputLayoutPasswordConfirm.editText!!.text.toString()
        val preferences = activity?.getSharedPreferences("account",Context.MODE_PRIVATE)

        //회원이 맞는지 확인하기
        if (preferences?.contains("id") == true){
            if (etPassword == etPasswordC){
                val savedPassword = preferences.getString("password","")
                if (etPassword == savedPassword){
                    Toast.makeText(requireContext(), "현재 ${id}님의 비밀번호와 같습니다.", Toast.LENGTH_SHORT).show()
                    this.pwPerfect = false
                    return false

                }else if (etPassword=="" && etPasswordC==""){
                    Toast.makeText(requireContext(), "아무것도 입력하지 않았습니다.", Toast.LENGTH_SHORT).show()
                    this.pwPerfect = false
                    return false
                }else{
                    Toast.makeText(requireContext(), "비밀번호 변경 가능. 저장버튼을 눌러주세요.", Toast.LENGTH_SHORT).show()
                    this.pwPerfect = true
                    return true //이 상황에서만 컨펌페스워드 메소드를 true시킨다.
                }
            }else{
                Toast.makeText(requireContext(), "비밀번호를 다시 확인해주세요. 서로 다릅니다.", Toast.LENGTH_SHORT).show()
                this.pwPerfect = false
                return false
            }
        }else{
            Toast.makeText(requireContext(), "회원이 아니세요. 회원가입 또는 로그인 후 이용해주세요", Toast.LENGTH_SHORT).show()
            this.pwPerfect = false
            return false

        }

    }





    private fun clickSaveBtn(){
        val preferences = activity?.getSharedPreferences("account",Context.MODE_PRIVATE)
        if (preferences?.contains("id") == true){
            saveUserAccount()
        }else{
            Toast.makeText(requireContext(), "기존회원이 아닙니다.\n회원가입 또는 로그인을 진행해주세요.", Toast.LENGTH_SHORT).show()
        }
    }




    private fun saveUserAccount(){//기존회원이 확실한 상황
        //val imageUrl1 = "http://baechu10.dothome.co.kr/06NoinGuin/${G.userAccountItem?.imgFile}"
        val newPassword = binding.inputLayoutPassword.editText!!.text.toString() // 입력한 새 비밀번호


            //1. 이미지체인지됬고 pw퍼펙트확인 true인 상황, 이미지,비번 둘다 서버,프리퍼런스,G 작업
        if (isImageChanged==true && pwPerfect==true){
            changeImgFile()
            if (confirmPassword(pwPerfect)) {
                changePassword(newPassword)
            }

        }else if (isImageChanged==true && pwPerfect==false){
            //2. 이미지체인지됬고, pw퍼펙트확인 false인 상황, 이미지만 서버,프리퍼런스,G 작업
            changeImgFile()
        }else if (isImageChanged==false && pwPerfect==true){
            //3. 이미지체인지 false이고, pw퍼펙트확인 true인 상황, 비번만 서버,프리퍼런스,G 작업
            if (confirmPassword(pwPerfect)) {
                changePassword(newPassword)
            }
        }else if (isImageChanged==false && pwPerfect==false){
            //4. 이미지,비번 둘다 안바꼈어-- 토스트 - 변경사항이 없습니다.
            Toast.makeText(requireContext(), "변경사항이 없습니다.", Toast.LENGTH_SHORT).show()
        }


    }//saveUserAccount()







    private fun changeImgFile(){

        //1.서버에 새로운 이미지 업데이트 요청
        val retrofitService = RetrofitHelper.getRetrofitInstance().create(RetrofitService::class.java)
        val sharedPreferences= activity?.getSharedPreferences("account",Context.MODE_PRIVATE)
        val id: String? = sharedPreferences?.getString("id","")
        val dataPart : MutableMap<String,String> = mutableMapOf()


        //dataPart에 담기
        if (id.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "사용자 id를 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
        } else{
            dataPart["id"] = id.toString()
        }

        //filePart에 담기
        val filePart : MultipartBody.Part? = imgRealPath.let {
            val file = File(it)
            val requestBody = RequestBody.create(MediaType.parse("image/*"),file)
            MultipartBody.Part.createFormData("img1",file.name,requestBody)
        }

        retrofitService.updateImage(dataPart,filePart).enqueue(object : Callback<MyInfomationDataChange>{
            override fun onResponse(
                call: Call<MyInfomationDataChange>,
                response: Response<MyInfomationDataChange>
            ) {
                val result = response.body()
                if (result?.rowNum!! > 0){
                    sharedPreferences?.edit()?.putString("imgFile", result.newData)?.apply()
                    G.userAccountItem?.imgFile = result.newData
                    AlertDialog.Builder(requireContext()).setMessage("프로필사진이 변경되었습니다.").create().show()
                }else{
                    AlertDialog.Builder(requireContext()).setMessage("${result.rowNum}: 못찾겠다").create().show()
                }
            }

            override fun onFailure(call: Call<MyInfomationDataChange>, t: Throwable) {
                Toast.makeText(requireContext(), "레트로핏 응답 없음 $t", Toast.LENGTH_SHORT).show()
            }
        })





    }//changeImgFile()



    private fun changePassword(newPassword:String){




        //1.서버에 새로운 이미지 업데이트 요청
        val retrofitService = RetrofitHelper.getRetrofitInstance().create(RetrofitService::class.java)
        val sharedPreferences= activity?.getSharedPreferences("account",Context.MODE_PRIVATE)
        val id: String? = sharedPreferences?.getString("id","")


        retrofitService.updatePassword(id!!, newPassword).enqueue(object : Callback<MyInfomationDataChange>{
            override fun onResponse(
                call: Call<MyInfomationDataChange>,
                response: Response<MyInfomationDataChange>
            ) {
                val result = response.body()
                AlertDialog.Builder(requireContext()).setMessage("${result?.rowNum}     ${result?.newData}").create().show()
            }

            override fun onFailure(call: Call<MyInfomationDataChange>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })




    }//changePassword()



}//fragment class..






