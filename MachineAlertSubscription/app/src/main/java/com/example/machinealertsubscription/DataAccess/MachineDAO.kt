package com.example.machinealertsubscription.DataAccess

import com.example.machinealertsubscription.BE.MachineWatch
import com.example.machinealertsubscription.UI.AlarmList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import com.example.machinealertsubscription.BE.Machine as Machine

class MachineDAO {
    suspend fun getMachines(watchId: String): Flow<Machine> {
        var response = listOf<Machine>()
        withContext(Dispatchers.IO) {
                var machines = RetrofitInstance.api.getMachines(watchId)
                    response = machines.execute().body()!!
        }

        var flowForMachines = flow {

            for (machine in response) {
                emit(machine)
            }
        }
        return flowForMachines
    }

    suspend fun subscribeToMachine(machineId: String, watchId: String) {
        val mw = MachineWatch(machineId, watchId)
        withContext(Dispatchers.IO) {
            RetrofitInstance.api.subscribeToMachine(mw).execute();
        }
    }

    suspend fun DeleteMachineSubscription(machineId: String, watchId: String) {
        val mw = MachineWatch(machineId, watchId)
        withContext(Dispatchers.IO) {
            RetrofitInstance.api.deleteMachineSubscription(mw).execute();
        }
    }
}