package com.example.machinealertsubscription.DataAccess

import com.example.machinealertsubscription.BE.Alarm
import com.example.machinealertsubscription.BE.Machine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.http.GET
import java.io.Console
import kotlin.random.Random

class FakeDB{

    private var machineId = 0
    private var alertId = 0
    private var  machines: MutableList<Machine> = mutableListOf()
    private var alerts: MutableList<Alarm> = mutableListOf()
    private val codeList: List<Int> = arrayListOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15)
    private val errorDescs: ArrayList<String> = arrayListOf(" needs an oil change", " needs an complete service", " the container is full", " coffee break", " Frederik skal have hjælp igen")
    constructor(){
        this.InitMachinesAndAlerts()
    }

    fun InitMachinesAndAlerts(){
        //initMachines

    }

    fun getMachines(): Flow<Machine> {
        return flow<Machine> {
            for(i in machineId until 100){
                val machine = Machine(i.toString())
                increaseMachineId()
                machines.add(machine)

                emit(machine)
                delay(10000)
            }
        }
    }


    suspend fun getAlerts(): Flow<Alarm>{
        return flow<Alarm> {
            for(i in alertId until 100){

                when(i){
                    in 0..20 -> {
                        val alert = Alarm(i, codeList[Random.nextInt(15)].toString(), errorDescs[Random.nextInt(4)])
                        increaseAlertId()
                        alerts.add(alert)
                        emit(alert)
                    }
                    in 21..40 -> {
                        val alert = Alarm(i, codeList[Random.nextInt(15)].toString(), errorDescs[Random.nextInt(4)])
                        increaseAlertId()
                        alerts.add(alert)
                        emit(alert)
                    }
                    in 41..60 -> {
                        val alert = Alarm(i, codeList[Random.nextInt(15)].toString(), errorDescs[Random.nextInt(4)])
                        increaseAlertId()
                        alerts.add(alert)
                        emit(alert)
                    }
                    in 61..80 -> {
                        val alert = Alarm(i, codeList[Random.nextInt(15)].toString(), errorDescs[Random.nextInt(4)])
                        increaseAlertId()
                        alerts.add(alert)
                        emit(alert)
                    }
                    in 81..100 -> {
                        val alert = Alarm(i, codeList[Random.nextInt(15)].toString(), errorDescs[Random.nextInt(4)])
                        increaseAlertId()
                        alerts.add(alert)
                        emit(alert)
                    }
                }
                delay(10000)
            }
        }
    }

    private fun increaseMachineId(): Int{
        var temp = machineId.inc()
        machineId = temp
        return machineId
    }

    private fun increaseAlertId(): Int{
        var temp = alertId.inc()
        alertId = temp
        return alertId
    }
}