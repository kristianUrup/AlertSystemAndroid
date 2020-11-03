package com.example.machinealertsubscription.Service

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.example.machinealertsubscription.UI.MainActivity
import com.microsoft.windowsazure.notifications.NotificationsHandler
import com.microsoft.windowsazure.notifications.NotificationsManager


class FirebaseService: NotificationsHandler() {
    private val TAG = "FirebaseService"
    val NOTIFICATION_CHANNEL_ID = "nh-demo-channel-id"
    val NOTIFICATION_CHANNEL_NAME = "Notification Hubs Demo Channel"
    val NOTIFICATION_CHANNEL_DESCRIPTION = "Notification Hubs Demo Channel"

    val NOTIFICATION_ID = 1
    private var mNotificationManager: NotificationManager? = null
    var builder: NotificationCompat.Builder? = null
    var ctx: Context? = null

    override fun onReceive(context: Context?, bundle: Bundle) {
        ctx = context
        val nhMessage = bundle.getString("message")
        sendNotification(nhMessage)
        if (MainActivity().isAcitivityVisible) {
            MainActivity().mainActivity.ToastNotify(nhMessage)
        }
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
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.description = NOTIFICATION_CHANNEL_DESCRIPTION
        channel.setShowBadge(true)
        val notificationManager =
            context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
        NotificationsManager.handleNotifications<FirebaseService>(context, "", FirebaseService::class.java)
    }
}