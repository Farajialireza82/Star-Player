package com.example.galaxyplayerkotlin.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.util.Log
import android.widget.Toast
import com.example.galaxyplayerkotlin.Fragments.LoginFragment

class NotificationReceiver : BroadcastReceiver() {

    val TAG = "NotificationReceiver"

    override fun onReceive(context: Context?, intent: Intent?) {

        Log.d(TAG, "onReceive has been called")

        val sharedPreferences: SharedPreferences =
            context!!.getSharedPreferences(
                LoginFragment.SHARED_PREFS,
                Context.MODE_PRIVATE
            )

        Log.d(TAG, "onReceive : sharedPreferences $sharedPreferences")

        val editor: Editor = sharedPreferences.edit()

        Log.d(TAG, "onReceive : Editor $editor")

        editor.putBoolean(LoginFragment.IS_LOGIN, false)

        Log.d(TAG, "editor.apply :) ")

        editor.apply()

        Log.d(TAG, "onReceive : And now IS_LOGIN must be false ")

        Log.d(TAG, "onReceive : IS_LOGIN is  ")

        val userName = sharedPreferences.getBoolean(
            LoginFragment.IS_LOGIN,
            false
        )

        Log.d(
            TAG,
            "orReceive: username = $userName"
        )

        Toast.makeText(context, "Data Reset Complete", Toast.LENGTH_SHORT).show()


    }

}