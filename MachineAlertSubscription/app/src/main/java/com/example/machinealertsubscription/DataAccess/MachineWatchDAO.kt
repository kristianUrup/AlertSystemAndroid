package com.example.machinealertsubscription.DataAccess

import com.example.machinealertsubscription.BE.MachineWatch
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class MachineWatchDAO {
    suspend fun getMachineWatches(watchId: String): Flow<MachineWatch> {
        var response = listOf<MachineWatch>()
        withContext(IO) {
            var machineWatches = RetrofitInstance.api.getMachineSubs(watchId)
            if(machineWatches.execute().code() == 200){
                response = machineWatches.execute().body()!!
            }
        }

        var flowForMachineWatches = flow {
            for (machine in response) {
                emit(machine)
            }
        }
        return flowForMachineWatches
    }
}