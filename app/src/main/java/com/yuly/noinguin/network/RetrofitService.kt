package com.yuly.noinguin.network

import com.yuly.noinguin.data.UserAccountItem
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Query

interface RetrofitService {

   //포스트 멀티파트로 유저계정을 서버에 보내기
    @Multipart
    @POST("/06NoinGuin/signup.php")
    fun postUserAccountToServer(@PartMap dataPart:Map<String,String>,
                                @Part filePart: MultipartBody.Part? ) : Call<String>






    //사용자입력한id,password줄게. 그걸 이용해서 서버에서 유저계정 GET방식으로 json array로 파싱해
    @GET("/06NoinGuin/login.php")
    fun loadUserAccountFromServer(@Query("id")id:String, @Query("password") password:String) : Call<UserAccountItem>




}