package com.example.machinealertsubscription.DataAccess

import kotlinx.coroutines.flow.Flow
import com.example.machinealertsubscription.BE.Machine as Machine

class MachineDAO {
    fun getMachines(): Flow<Machine> {
        val fakeDB = FakeDB()
        return fakeDB.getMachines()
    }
}