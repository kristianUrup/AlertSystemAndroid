package com.example.machinealertsubscription.Service


import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService


class InstanceIdService: FirebaseMessagingService() {

    private val TAG = "MyInstanceIDService"


    fun onNewToken() {
        Log.d(TAG, "Refreshing FCM Registration Token")
        val intent = Intent(this, RegistrationIntentService::class.java)
        startService(intent)
    }
}