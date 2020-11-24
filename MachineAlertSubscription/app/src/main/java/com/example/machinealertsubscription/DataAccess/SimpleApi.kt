package com.example.machinealertsubscription.DataAccess


import com.example.machinealertsubscription.BE.Alarm
import retrofit2.http.GET
import com.example.machinealertsubscription.BE.Machine
import com.example.machinealertsubscription.BE.MachineWatch
import retrofit2.Call
import retrofit2.http.Path

interface SimpleApi {

    @GET("alarms")
    fun getAlarms(): Call<List<Alarm>>

    @GET("machines")
    fun getMachines(): Call<List<Machine>>

    @GET("machinesubs/{watchId}")
    fun getMachineSubs(@Path( "watchId") watchId: String): Call<List<MachineWatch>>
}