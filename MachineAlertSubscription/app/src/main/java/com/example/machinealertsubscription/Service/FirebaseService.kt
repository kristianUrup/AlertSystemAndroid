package com.example.machinealertsubscription.Service

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.machinealertsubscription.UI.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class FirebaseService: FirebaseMessagingService() {
    private val TAG = "FirebaseService"

    val NOTIFICATION_CHANNEL_ID = "nh-demo-channel-id"
    val NOTIFICATION_CHANNEL_NAME = "Notification Hubs Demo Channel"
    val NOTIFICATION_CHANNEL_DESCRIPTION = "Notification Hubs Demo Channel"

    val NOTIFICATION_ID = 1
    private var mNotificationManager: NotificationManager? = null
    var builder: NotificationCompat.Builder? = null
    var ctx: Context? = null

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.from)
        val nhMessage: String?
        // Check if message contains a notification payload.
        nhMessage = if (remoteMessage.notification != null) {
            Log.d(
                TAG,
                "Message Notification Body: " + remoteMessage.notification!!.body
            )
            remoteMessage.notification!!.body
        } else {
            remoteMessage.data.values.iterator().next()
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        if (MainActivity().isActivityVisible) {
            MainActivity().mainActivity.ToastNotify(nhMessage)
        }
        sendNotification(nhMessage)
    }

    private fun sendNotification(msg: String?) {
        val intent = Intent(ctx, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        mNotificationManager =
            ctx!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val contentIntent = PendingIntent.getActivity(
            ctx, 0,
            intent, PendingIntent.FLAG_ONE_SHOT
        )
        val defaultSoundUri =
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
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

    fun createChannelAndHandleNotifications(context: Context) {
        ctx = context
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = NOTIFICATION_CHANNEL_DESCRIPTION
            channel.setShowBadge(true)
            val notificationManager =
                context.getSystemService(
                    NotificationManager::class.java
                )
            notificationManager.createNotificationChannel(channel)
        }
    }
}