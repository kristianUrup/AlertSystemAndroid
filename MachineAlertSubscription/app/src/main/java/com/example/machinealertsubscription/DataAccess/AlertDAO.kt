package com.example.machinealertsubscription.DataAccess

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.content.res.AssetManager.ACCESS_BUFFER
import android.net.Uri
import com.example.machinealertsubscription.BE.Alert
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.http.GET
import java.io.BufferedReader
import java.io.FileReader
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.channels.FileChannel.open

class AlertDAO  {

    constructor()

    suspend fun getAlerts(): Flow<Alert> {


/*        val am = _context!!.assets
        val objects = am.open("DBServer.json", ACCESS_BUFFER)
        val br = BufferedReader(InputStreamReader(objects))
        val body = "//assets/DBServer.json".httpGet().body
        println(body)
*/

        /*val coroutine1 = withContext(Dispatchers.IO) {
            val (request, response, result) = "https://api.mocki.io/v1/304e2522".httpGet()
                .header("Accept", "application/json")
                .response()



            when (result) {
            is Result.Failure -> {
                println("Inside the fuel Failure result ${String(response.data)}")
                return@withContext String(response.data)
            }
                is Result.Success -> {
                    val data = result.get()
                    println("Inside the Fuel Data Succes Result $data")
                    return@withContext data
                }
            }
        }
        return coroutine1

         */
        var fakeDb = FakeDB()
        return fakeDb.getAlerts()
    }
}