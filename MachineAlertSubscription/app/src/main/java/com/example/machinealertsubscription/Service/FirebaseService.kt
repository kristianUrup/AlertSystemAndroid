package com.example.machinealertsubscription.Service

import android.R
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.machinealertsubscription.UI.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class FirebaseService: FirebaseMessagingService() {
    private val TAG = "FirebaseService"
    val NOTIFICATION_CHANNEL_ID = "nh-demo-channel-id"
    val NOTIFICATION_ID = 1

    private var mNotificationManager: NotificationManager? = null
    var ctx: Context? = null

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        val intent = Intent(this, RegistrationIntentService::class.java)
        startService(intent)
    }
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val nhMessage: String? = if (remoteMessage.notification != null) {
            remoteMessage.notification!!.body
        } else {
            remoteMessage.data.values.iterator().next()
        }

        sendNotification(nhMessage)
    }

    private fun sendNotification(msg: String?) {
        ctx = this
        val intent = Intent(ctx, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        mNotificationManager =
            ctx!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val contentIntent = PendingIntent.getActivity(
            ctx, 0,
            intent, PendingIntent.FLAG_ONE_SHOT
        )
        val notificationBuilder =
            NotificationCompat.Builder(
                ctx!!,
                NOTIFICATION_CHANNEL_ID
            )
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_popup_reminder)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
        notificationBuilder.setContentIntent(contentIntent)
        mNotificationManager!!.notify(NOTIFICATION_ID, notificationBuilder.build())
    }
}