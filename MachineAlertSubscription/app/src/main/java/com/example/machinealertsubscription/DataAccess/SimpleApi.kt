package com.example.machinealertsubscription.DataAccess

import com.example.machinealertsubscription.BE.Alarm
import com.example.machinealertsubscription.BE.AlarmWatch
import com.example.machinealertsubscription.BE.Machine
import com.example.machinealertsubscription.BE.MachineWatch
import retrofit2.Call
import retrofit2.http.*

interface SimpleApi {

    @GET("alarms/{watchId}")
    fun getAlarms(@Path("watchId") watchId: String): Call<List<Alarm>>

    @GET("machines/{watchId}")
    fun getMachines(@Path("watchId") watchId: String): Call<List<Machine>>

    @POST("subscribeToAlarm")
    fun subscribeToAlarm(@Body alarmWatch: AlarmWatch): Call<Void>

    @POST("subscribeToMachine")
    fun subscribeToMachine(@Body machineWatch: MachineWatch): Call<Void>

    @HTTP(method = "DELETE", path = "alarmSubs", hasBody = true)
    fun deleteAlarmSubscription(@Body alarmWatch: AlarmWatch) : Call<Void>

    @HTTP(method = "DELETE", path = "machineSubs", hasBody = true)
    fun deleteMachineSubscription(@Body machineWatch: MachineWatch) : Call<Void>
}
