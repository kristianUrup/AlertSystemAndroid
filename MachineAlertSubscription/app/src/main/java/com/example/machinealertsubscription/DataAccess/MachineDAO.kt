package com.example.machinealertsubscription.DataAccess

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import com.example.machinealertsubscription.BE.Machine as Machine

class MachineDAO {
    suspend fun getMachines(): Flow<Machine> {
        var response = listOf<Machine>()
        withContext(Dispatchers.IO) {
            var machines = RetrofitInstance.api.getMachines()
            response = machines.execute().body()!!
        }

        var flowForMachines = flow {

            for (machine in response) {
                emit(machine)
            }
        }
        return flowForMachines
    }
}