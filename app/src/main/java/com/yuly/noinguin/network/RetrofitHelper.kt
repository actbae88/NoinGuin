package com.yuly.noinguin.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper {

    companion object{
        fun getRetrofitInstance() : Retrofit{

            val builder = Retrofit.Builder()
            builder.baseUrl("http://baechu10.dothome.co.kr")
            builder.addConverterFactory(GsonConverterFactory.create())
            val retrofit:Retrofit = builder.build()

            return retrofit
        }
    }
}