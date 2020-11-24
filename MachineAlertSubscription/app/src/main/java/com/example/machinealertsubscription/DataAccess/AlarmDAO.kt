package com.example.machinealertsubscription.DataAccess

import com.example.machinealertsubscription.BE.Alarm
import com.example.machinealertsubscription.BE.AlarmWatch
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext


class AlarmDAO  {

    constructor()

    suspend fun getAlarms(): Flow<Alarm> {
        var response = listOf<Alarm>()
        withContext(IO) {
            var alarms = RetrofitInstance.api.getAlarms()
            response = alarms.execute().body()!!
        }

        var flowForAlarms = flow {
            for (i in response)
            {
                emit(i)
            }
        }




        /*response.enqueue(object : Callback<List<AlarmResponse>> {
            override fun onResponse(call: Call<List<AlarmResponse>>, response: Response<List<AlarmResponse>>) {
                Log.d("HTTP", "Successful")
            }

            override fun onFailure(call: Call<List<AlarmResponse>>, t: Throwable) {
                Log.d("HTTP", "Failure")
            }

        })*/

        /*if (response.isSuccessful) {
            alarms = response.body() as List<Alarm>
            Log.d("HTTP", "Call was successful");
        }*/
        return flowForAlarms
    }
    suspend fun subscribeToAlarm(alarmId: Int, watchId: String) {
        val aw = AlarmWatch(alarmId, watchId)
        withContext(IO) {
            var req = RetrofitInstance.api.subscribeToAlarm(aw)
            req.execute()
        }
    }
}