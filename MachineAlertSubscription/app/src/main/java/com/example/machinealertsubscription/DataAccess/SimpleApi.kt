package com.example.machinealertsubscription.DataAccess

import com.example.machinealertsubscription.BE.Alarm
import com.example.machinealertsubscription.BE.AlarmWatch
import com.example.machinealertsubscription.BE.Machine
import com.example.machinealertsubscription.BE.MachineWatch
import retrofit2.Call
import retrofit2.http.*

interface SimpleApi {

    @GET("alarms")
    fun getAlarms(): Call<List<Alarm>>

    @GET("machines")
    fun getMachines(): Call<List<Machine>>

    @POST("subscribeToAlarm")
    fun subscribeToAlarm(@Body alarmWatch: AlarmWatch): Call<Void>

    @POST("subscribeToMachine")
    fun subscribeToMachine(@Body machineWatch: MachineWatch): Call<Void>
}
