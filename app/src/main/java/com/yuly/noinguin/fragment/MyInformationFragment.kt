package com.yuly.noinguin.fragment

import android.app.AlertDialog
import android.content.Intent
import android.database.Cursor
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toBitmap
import androidx.loader.content.CursorLoader
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.yuly.noinguin.R
import com.yuly.noinguin.data.UserAccountItem
import com.yuly.noinguin.databinding.FragmentMyInformationBinding
import com.yuly.noinguin.network.G
import com.yuly.noinguin.network.RetrofitHelper
import com.yuly.noinguin.network.RetrofitService
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

        binding.btnOk.setOnClickListener { saveUserAccount() }


    }//온크리



    private fun changePhoto(){

        //쉐어드프리퍼런스에 account 없으면 토스트띄우기 "회원가입 또는 로그인을 하셔야 합니다."

        val intent = if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU) Intent(MediaStore.ACTION_PICK_IMAGES) else Intent(Intent.ACTION_OPEN_DOCUMENT).setType("image/*")
        //AlertDialog.Builder(requireContext()).setMessage(imgFile).create().show()
        resultLauncher.launch(intent)

        //바뀐 imgRealPath를 서버로 UPDATE하자

    }

    //멤버변수에..부릉이 리절트런처
    val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        val uri: Uri? = it.data?.data
        uri?.let {
            Glide.with(requireContext()).load(uri).into(binding.iv)
            //binding.tv.text = uri.toString() -- content://media/picker/0/com/android/providers.media...절대경로로 바꿔야함!!
            imgRealPath = getRealPathFromUri(uri)
            //binding.tv.text = imgRealPath --/storage/emulated/0/Android/data/com/yuly.noinguin/cache/1000..jpg
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

    }//이미지 리얼패스얻기




    //채팅--로그인화면
    private fun saveUserAccount(){
//        val imageUrl1 = "http://baechu10.dothome.co.kr/06NoinGuin/${G.userAccountItem?.imgFile}"
//        //회원가입 또는 로그인을 하지않은 상황이면,G에 null이니..
//        if (imgFile.equals(null)){
//            Toast.makeText(requireContext(), "회원가입 또는 로그인을 해주세요", Toast.LENGTH_SHORT).show()
//        }else if (imgFile.equals())
//            binding.iv.drawable.toString()
//            //imgFile : ./signupimg/IMG_2024~~.jpg

        AlertDialog.Builder(requireContext()).setMessage(binding.iv.drawable.toString()).create().show()
        //binding.iv.drawable.toString()

    }






}