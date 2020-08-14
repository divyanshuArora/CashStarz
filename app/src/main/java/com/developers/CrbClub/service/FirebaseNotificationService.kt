package com.developers.CrbClub.service

import android.annotation.SuppressLint
import android.app.*
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.developers.CrbClub.DashboardActivity
import com.developers.CrbClub.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class FirebaseNotificationService : FirebaseMessagingService() {
    val TAG = "FirebaseService"
    private var mMessage: String? = null
    private var title: String? = null
    lateinit var intent: Intent

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.e(TAG, "Message data payload: " + remoteMessage.data)
        try {
            val params = remoteMessage.data
            mMessage = params.get("text")
            title = params.get("title")
            //var extra_data = JSONObject(params["extra_data"])
            Log.e(TAG, "Data: " + mMessage!!)
//            Log.e(TAG, "Data: " + "notification_type  " + extra_data.getString("notification_type"))

            sendNotificationOreoUp(title!!, mMessage!!)
        } catch (e: Exception) {
            Log.e(TAG, "Data Exception: " + e.message)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("WrongConstant")
    private fun sendNotificationOreoUp(title: String, body: String) {

        intent = Intent(this, DashboardActivity::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            Log.e("FirebaseSErvice", "notification1")
            val pendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
            }

            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val id = getString(R.string.app_name)

            var mChannel: NotificationChannel? = null

            mChannel = NotificationChannel(
                id,
                getString(R.string.default_notification_channel_id),
                IMPORTANCE_HIGH
            )

            mChannel.description = body
            mChannel.enableLights(true)
            mChannel.lightColor = Color.RED
            notificationManager.createNotificationChannel(mChannel)


            val notificationBuilder = Notification.Builder(this, id)

                .setSmallIcon(R.mipmap.ic_launcher_round)

                .setContentTitle(id)

                .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher_round))
                .setContentText(body)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSound(defaultSoundUri)

            notificationBuilder.setContentIntent(pendingIntent)


            notificationManager.notify(111 /* ID of notification */, notificationBuilder.build())
        } else {
            val pendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
            }
            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val id = getString(R.string.app_name)


            val notificationBuilder = NotificationCompat.Builder(this, id)

                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(id)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher_round))
                .setContentText(body)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(defaultSoundUri)

            notificationBuilder.setContentIntent(pendingIntent)


            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
        }

    }

}



