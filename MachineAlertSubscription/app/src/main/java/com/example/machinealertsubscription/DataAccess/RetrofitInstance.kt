package com.example.machinealertsubscription.DataAccess

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

     val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:7071/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: SimpleApi by lazy {
        retrofit.create(SimpleApi::class.java)
    }
}