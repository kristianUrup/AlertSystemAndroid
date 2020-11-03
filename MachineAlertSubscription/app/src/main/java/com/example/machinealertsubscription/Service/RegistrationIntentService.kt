package com.example.machinealertsubscription.Service

import android.app.IntentService
import android.content.Intent
import android.util.Log
import androidx.preference.PreferenceManager
import com.example.machinealertsubscription.BE.NotificationSettings
import com.example.machinealertsubscription.UI.MainActivity
import com.google.firebase.iid.FirebaseInstanceId
import com.microsoft.windowsazure.messaging.NotificationHub
import java.util.concurrent.TimeUnit

class RegistrationIntentService: IntentService("RegIntentService") {
    private val TAG = "RegIntentService"

    private val notificationSettings = NotificationSettings()

    override fun onHandleIntent(intent: Intent?) {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener { task ->
                var fcmToken: String? = null
                var resultString = ""
                if (task.isSuccessful) {
                    fcmToken = task.result!!.token
                    resultString = "Got the token: + $fcmToken"
                } else if (!task.isSuccessful) {
                    resultString = "Did not get token"
                }
                if (fcmToken != null) {
                    try {
                        val hub = NotificationHub(
                            "mobile-app-notification",
                            "Endpoint=sb://alarm-system-notification.servicebus.windows.net/;SharedAccessKeyName=DefaultListenSharedAccessSignature;SharedAccessKey=RLHwGy5sFgmpEt1uF3HzhsCc6YQbinr8UK5MKwxh4f4=",
                            this@RegistrationIntentService
                        )
                        hub.register(fcmToken)
                    } catch (e: java.lang.Exception) {
                        Log.d(TAG, e.message)
                    }
                }
                if (MainActivity().isAcitivityVisible) {
                    MainActivity().mainActivity.ToastNotify(resultString)
                }
            }
    }
}