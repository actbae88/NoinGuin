package com.yuly.noinguin.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.yuly.noinguin.R
import com.yuly.noinguin.data.UserAccountItem
import com.yuly.noinguin.databinding.FragmentMyInformationBinding
import com.yuly.noinguin.network.RetrofitHelper
import com.yuly.noinguin.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyInformationFragment : Fragment() {

   val binding by lazy { FragmentMyInformationBinding.inflate(layoutInflater) }
//    lateinit var id:String
//    lateinit var password:String
//    lateinit var imgFile:String



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        //AlertDialog.Builder(requireContext()).setMessage(id+password+imgFile).create().show()


        // loadServer()
    }


//    private fun loadServer(){
//
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