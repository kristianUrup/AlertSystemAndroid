package com.example.machinealertsubscription.DataAccess

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
        var machine1 = Machine(machineId+1);
        var machine2 = Machine(machineId+1);
        var machine3 = Machine(machineId+1);
        machines.plus(machine1);
        machines.plus(machine2);
        machines.plus(machine3);

        println(machines);

    }
}