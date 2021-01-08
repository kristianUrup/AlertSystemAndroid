package com.example.machinealertsubscription.Service

import android.app.IntentService
import android.content.Intent
import android.util.Log
import androidx.preference.PreferenceManager
import com.example.machinealertsubscription.BE.NotificationSettings
import com.example.machinealertsubscription.UI.MainActivity
import com.google.firebase.iid.FirebaseInstanceId
import com.microsoft.windowsazure.messaging.NotificationHub
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class RegistrationIntentService: IntentService("RegIntentService") {
    private val TAG = "RegIntentService"

    private val notificationSettings = NotificationSettings()

    override fun onHandleIntent(intent: Intent?) {
        println(FirebaseInstanceId.getInstance().instanceId)
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener { task ->
                val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
                var fcmToken: String? = null
                var resultString = ""
                if (task.isSuccessful) {
                    fcmToken = task.result!!.token
                    resultString = "Got the token: + $fcmToken"
                } else if (!task.isSuccessful) {
                    resultString = "Did not get token"
                }
                if (fcmToken != null) {
                    val tokenFromPreferences = sharedPreferences.getString("FCMToken","")
                        if(tokenFromPreferences != fcmToken){
                            try {
                                val hub = NotificationHub(
                                    notificationSettings.HubName,
                                    notificationSettings.HubListenConnectionString,
                                    applicationContext
                                )
                                CoroutineScope(IO).launch {
                                    hub.register(fcmToken)
                                    sharedPreferences.edit().putString("FCMToken", fcmToken).apply()
                                }
                            } catch (e: java.lang.Exception) {
                                e.printStackTrace()
                                resultString = "error occurred"
                            }
                        } else {
                            resultString = "Already registered"
                        }
                }
                Log.d(TAG, resultString)
                if (MainActivity().isAcitivityVisible) {
                    MainActivity().mainActivity.ToastNotify(resultString)
                }
            }
    }
}