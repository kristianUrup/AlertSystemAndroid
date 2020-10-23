package com.example.machinealertsubscription.DataAccess

import com.example.machinealertsubscription.BE.Alert
import com.example.machinealertsubscription.BE.Machine
import java.io.Console

class FakeDB{

    private var machineId = 0
    private var alertId = 0
    private var  machines: MutableList<Machine> = mutableListOf()
    private var alerts: MutableList<Alert> = mutableListOf()
    constructor(){
        this.InitMachinesAndAlerts();
    }

    public fun InitMachinesAndAlerts(){
        //initMachines
        var machine1 = Machine(increaseMachineId())
        var machine2 = Machine(increaseMachineId())
        var machine3 = Machine(increaseMachineId())
        var machine4 = Machine(increaseMachineId())
        var machine5 = Machine(increaseMachineId())
        var machine6 = Machine(increaseMachineId())
        var machine7 = Machine(increaseMachineId())
        machines.add(machine1)
        machines.add(machine2)
        machines.add(machine3)
        machines.add(machine4)
        machines.add(machine5)
        machines.add(machine6)
        machines.add(machine7)

        val oilServiceString = " needs a oil service"
        val serviceString = " needs a complete service"
        var alert1 = Alert(increaseAlertId(), oilServiceString, machine1)
        var alert2 = Alert(increaseAlertId(), oilServiceString, machine1)
        var alert3 = Alert(increaseAlertId(), serviceString, machine2)
        var alert4 = Alert(increaseAlertId(), serviceString, machine3)
        var alert5 = Alert(increaseAlertId(), oilServiceString, machine2)
        var alert6 = Alert(increaseAlertId(), serviceString, machine3)
        var alert7 = Alert(increaseAlertId(), oilServiceString, machine1)

        alerts.add(alert1)
        alerts.add(alert2)
        alerts.add(alert3)
        alerts.add(alert4)
        alerts.add(alert5)
        alerts.add(alert6)
        alerts.add(alert7)
    }

    public fun getMachines(): List<Machine>{
        return this.machines
    }

    public fun getAlerts(): List<Alert>{
        return this.alerts
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