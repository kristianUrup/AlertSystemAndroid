package com.example.machinealertsubscription.DataAccess


import com.example.machinealertsubscription.BE.Alarm
import retrofit2.http.GET
import com.example.machinealertsubscription.BE.Machine
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface SimpleApi {

    @GET("alarms")
    fun getAlarms(): Call<List<Alarm>>

    @GET("machines")
    fun getMachines(): Call<List<Machine>>

    @POST("subscribeToAlarm")
    fun subscribeToAlarm(@Body alarmId: Int, watchId: String): Call<Void>

    @POST("subscribeToMachine/{machineId}/{watchId}")
    fun subscribeToMachine(@Path("machineId") machineId: String, @Path("watchId") watchId: String): Call<Void>

}