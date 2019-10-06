package com.example.galaxyplayerkotlin.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.galaxyplayerkotlin.Activities.MainActivityClass
import com.example.galaxyplayerkotlin.R
import com.example.galaxyplayerkotlin.service.App.CHANNEL_ID
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class FBSMessagingServiceClass : FirebaseMessagingService() {

    private val TAG = "FBSServiceClass"


    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

        Log.d(TAG, " onMessageReceived")


        val title = p0.notification!!.title

        val body = p0.notification!!.body

        Log.d(TAG, "title = $title & body = $body ")

        val intent = Intent(this, MainActivityClass::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        val pendingIntent = PendingIntent

            .getActivity(
                this,
                0,
                intent,
                FLAG_ONE_SHOT
            )


        val resetDataIntent = Intent(this, NotificationReceiver::class.java)

        val resetDataPendingIntent =
            PendingIntent.getBroadcast(this, 0, resetDataIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationBuilder = createNotif(title, body, pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (p0.data["COM"] != null) {

            Log.d(TAG, "COM isn't null")

            if (p0.data["COM"] == "RESET") {

                Log.d(TAG, "Command is to reset isn't null")



                notificationBuilder.setColor(Color.BLUE)
                    .addAction(R.mipmap.icon, "Reset_data", resetDataPendingIntent)


            }
        }

        notificationManager.notify(0, notificationBuilder.build())


    }

    private fun createNotif(
        notifTitle: String?,
        notifBody: String?,
        notifPendingIntent: PendingIntent
    ): NotificationCompat.Builder {

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(notifTitle)
            .setContentText(notifBody)
            .setAutoCancel(true)
            .setSmallIcon(R.mipmap.icon)
            .setContentIntent(notifPendingIntent)

    }


}
