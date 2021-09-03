package com.example.galaxyplayerkotlin.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.util.Log
import android.widget.Toast

class NotificationReceiver : BroadcastReceiver() {

    val TAG = "NotificationReceiver"

    override fun onReceive(context: Context?, intent: Intent?) {

        Log.d(TAG, "onReceive has been called")



    }

}