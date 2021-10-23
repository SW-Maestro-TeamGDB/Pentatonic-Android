package com.team_gdb.pentatonic.firebase

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber
import android.R
import android.app.Notification

import android.app.NotificationManager

import android.app.NotificationChannel

import android.os.Build
import androidx.core.app.NotificationCompat

import androidx.core.app.NotificationManagerCompat
import com.apollographql.apollo.rx3.rxMutate
import com.team_gdb.pentatonic.UpdateDeviceTokenMutation
import com.team_gdb.pentatonic.network.NetworkHelper.apolloClient
import com.team_gdb.pentatonic.network.applySchedulers
import com.team_gdb.pentatonic.type.UpdateDeviceTokenInput
import io.reactivex.rxjava3.kotlin.subscribeBy


class MyFirebaseMessagingService : FirebaseMessagingService() {
    /**
     * Called if the FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * FCM registration token is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        Timber.d("Refreshed token: $token")
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
//        sendRegistrationToServer(token)

    }

    /**
     * 메세지 수신했을 때 호출되는 콜백
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val notificationManager = NotificationManagerCompat.from(
            applicationContext
        )

        var builder: NotificationCompat.Builder? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager.getNotificationChannel(CHANNEL_ID) == null) {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                notificationManager.createNotificationChannel(channel)
            }
            builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
        } else {
            builder = NotificationCompat.Builder(applicationContext)
        }

        val title: String = remoteMessage.notification!!.title!!
        val body: String = remoteMessage.notification!!.body!!


        builder.setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.mipmap.sym_def_app_icon)

        val notification: Notification = builder.build()
        notificationManager.notify(1, notification)
    }

    companion object {
        const val CHANNEL_ID = "pentatonic"
        const val CHANNEL_NAME = "pentatonic"
    }
}