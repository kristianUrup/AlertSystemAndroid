package com.example.machinealertsubscription.DataAccess


import com.example.machinealertsubscription.BE.Alarm
import retrofit2.http.GET
import com.example.machinealertsubscription.BE.Machine
import retrofit2.Call

interface SimpleApi {

    @GET("alarms")
    fun getAlarms(): Call<List<Alarm>>

    @GET("machines")
    fun getMachines(): Call<List<Machine>>
}