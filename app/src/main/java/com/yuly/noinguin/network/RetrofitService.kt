package com.yuly.noinguin.network

import com.yuly.noinguin.data.LoginResponse
import com.yuly.noinguin.data.UserAccountItem
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface RetrofitService {

   //회원가입-> 포스트 멀티파트로 유저계정을 서버에 보내기
    @Multipart
    @POST("/06NoinGuin/signup.php")
    fun postUserAccountToServer(@PartMap dataPart:Map<String,String>,
                                @Part filePart: MultipartBody.Part? ) : Call<String>






    //로그인-> 사용자입력한id,password줄게. 그걸 이용해서 서버에서 유저계정 POST방식으로 json array로 파싱해

    @FormUrlEncoded
    @POST("/06NoinGuin/login.php")
    fun loadUserAccountFromServer(@Field("id") id:String, @Field("password") password:String)  : Call<LoginResponse>





}