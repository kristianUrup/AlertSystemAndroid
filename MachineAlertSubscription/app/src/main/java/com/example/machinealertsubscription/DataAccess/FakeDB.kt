package com.example.machinealertsubscription.DataAccess

import com.example.machinealertsubscription.BE.Alert
import com.example.machinealertsubscription.BE.Machine
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import retrofit2.http.GET
import java.io.Console
import kotlin.random.Random

class FakeDB{

    private var machineId = 0
    private var alertId = 0
    private var  machines: MutableList<Machine> = mutableListOf()
    private var alerts: MutableList<Alert> = mutableListOf()
    private val codeList: List<Int> = arrayListOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15)
    private val errorDescs: ArrayList<String> = arrayListOf(" needs an oil change", " needs an complete service", " the container is full", " coffee break", " Frederik skal have hjælp igen")
    constructor(){
        this.InitMachinesAndAlerts();
    }

    public fun InitMachinesAndAlerts(){
        //initMachines

    }

    public fun getMachines(): Flow<Machine> {
        return flow<Machine> {
            for(i in machineId until 100){
                val machine = Machine(i)
                increaseMachineId()
                machines.add(machine)

                emit(machine)
                delay(10000)
            }
        }
    }


    public suspend fun getAlerts(): Flow<Alert>{
        return flow<Alert> {
            for(i in alertId until 100){

                when(i){
                    in 0..20 -> {
                        val alert = Alert(i, codeList[Random.nextInt(15)].toString(), errorDescs[Random.nextInt(4)])
                        increaseAlertId()
                        alerts.add(alert)
                        emit(alert)
                    }
                    in 21..40 -> {
                        val alert = Alert(i, codeList[Random.nextInt(15)].toString(), errorDescs[Random.nextInt(4)])
                        increaseAlertId()
                        alerts.add(alert)
                        emit(alert)
                    }
                    in 41..60 -> {
                        val alert = Alert(i, codeList[Random.nextInt(15)].toString(), errorDescs[Random.nextInt(4)])
                        increaseAlertId()
                        alerts.add(alert)
                        emit(alert)
                    }
                    in 61..80 -> {
                        val alert = Alert(i, codeList[Random.nextInt(15)].toString(), errorDescs[Random.nextInt(4)])
                        increaseAlertId()
                        alerts.add(alert)
                        emit(alert)
                    }
                    in 81..100 -> {
                        val alert = Alert(i, codeList[Random.nextInt(15)].toString(), errorDescs[Random.nextInt(4)])
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