package com.yuly.noinguin.network

import com.yuly.noinguin.data.LoginResponse
import com.yuly.noinguin.data.MyInfomationDataChange
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
                                @Part filePart: MultipartBody.Part? ) : Call<LoginResponse>




    //회원가입때 입력된아이디를 서버아이디들과 중복확인하기
    @FormUrlEncoded
    @POST("/06NoinGuin/idconfirm.php")
    fun confirmIdFromServer(@Field("id") id:String) : Call<String>




    //로그인-> 사용자입력한id,password줄게. 그걸 이용해서 서버에서 유저계정 POST방식으로 json array로 파싱해

    @FormUrlEncoded
    @POST("/06NoinGuin/login.php")
    fun loadUserAccountFromServer(@Field("id") id:String, @Field("password") password:String)  : Call<LoginResponse>



    //MyInformationFragment에서 이미지변경시 서버 업데이트 요청해줘
    //파일은 일반데이터가 아니다. 물고기 아이스박스포장
    @Multipart
    @POST("/06NoinGuin/updateImage.php")
    fun updateImage(@PartMap dataPart: Map<String, String>,
                    @Part filePart: MultipartBody.Part?) : Call<MyInfomationDataChange>
    //MultipartBody.Part라는 객체를 만들어서 보내줄게.
    //string은 key=value 이런식으로 담아보내면된다.  header에는 인증키같은거 추가적인거 넣는곳이다.




    //MyInformationFragment에서 비밀번호 변경시 서버 업데이트 요청해줘
    @FormUrlEncoded
    @POST("/06NoinGuin/updatePassword.php")
    fun updatePassword(@Field("id") id:String, @Field("password") password:String) : Call<MyInfomationDataChange>







}