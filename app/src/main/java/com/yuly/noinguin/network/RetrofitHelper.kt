package com.yuly.noinguin.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetrofitHelper {

    companion object{
        fun getRetrofitInstance() : Retrofit{

            val builder = Retrofit.Builder()
            builder.baseUrl("http://baechu10.dothome.co.kr")
            builder.addConverterFactory(ScalarsConverterFactory.create())
            builder.addConverterFactory(GsonConverterFactory.create())

            return builder.build()
        }
    }
}