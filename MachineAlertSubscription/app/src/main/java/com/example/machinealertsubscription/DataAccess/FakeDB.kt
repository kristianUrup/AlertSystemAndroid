package com.example.machinealertsubscription.DataAccess

import com.example.machinealertsubscription.BE.Alert
import com.example.machinealertsubscription.BE.Machine
import java.io.Console

class FakeDB{

    private val machineId = 1;
    private val machines = List<Machine>(0) { Machine(machineId+1)}
    constructor(){
        this.InitMachinesAndAlerts();
    }

    public fun InitMachinesAndAlerts(){
        //initMachines
        var machine1 = Machine(machineId+1)
        var machine2 = Machine(machineId+1)
        var machine3 = Machine(machineId+1)
        machines.plus(machine1)
        machines.plus(machine2)
        machines.plus(machine3)

        val oilServiceString = " needs a oil service"
        val serviceString = " needs a complete service"
        var alert1 = Alert(1, oilServiceString, machine1)
        var alert2 = Alert(2, serviceString, machine1)


    }

    public fun getMachines(): List<Machine>{
        return this.machines
    }
}