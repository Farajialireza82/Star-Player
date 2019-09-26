package com.example.galaxyplayerkotlin.service

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.util.Log
import com.example.galaxyplayerkotlin.Activities.MainActivityClass
import com.example.galaxyplayerkotlin.Fragments.LoginFragment
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FBSServiceClass : FirebaseMessagingService() {

    private val TAG = "FBSServiceClass"

    private val RESET = "reset"

    override fun onCreate() {
        super.onCreate()

        Log.d(TAG, "onCreate ")
    }


    override fun onMessageReceived(p0: RemoteMessage?) {
        super.onMessageReceived(p0)

        Log.d(TAG, "onMessageReceived: " + p0?.data.toString())



        when {
            p0?.data?.get("COM") != null ->
                when (p0?.data?.get("COM")) {
                    RESET -> {

                        Log.d(TAG, " command is to reset")

                        val sharedPreferences: SharedPreferences =
                            baseContext.getSharedPreferences(
                                LoginFragment.SHARED_PREFS,
                                Context.MODE_PRIVATE
                            )
                        val editor: Editor = sharedPreferences.edit()

                        editor.putBoolean(
                            LoginFragment.IS_LOGIN,
                            false
                        )

                        editor.apply()

                    }
                }
        }


    }


}
