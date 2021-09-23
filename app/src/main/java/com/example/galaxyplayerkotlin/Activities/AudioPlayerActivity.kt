/*
package com.example.galaxyplayerkotlin.Activities

import MusicServiceClass
import android.content.ComponentName
import android.net.Uri
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.galaxyplayerkotlin.R

class AudioPlayerActivity : AppCompatActivity() {

    private lateinit var mMediaBrowserCompat: MediaBrowserCompat
    private val connectionCallback: MediaBrowserCompat.ConnectionCallback =
        object : MediaBrowserCompat.ConnectionCallback() {

            override fun onConnected() {

                // The browser connected to the session successfully, use the token to create the controller
                super.onConnected()
                mMediaBrowserCompat.sessionToken.also { token ->
                    val mediaController = MediaControllerCompat(this@PlayerActivity, token)
                    MediaControllerCompat.setMediaController(this@PlayerActivity, mediaController)
                }
                playPauseBuild()
                Log.d("onConnected", "Controller Connected")
            }

            override fun onConnectionFailed() {
                super.onConnectionFailed()
                Log.d("onConnectionFailed", "Connection Failed")

            }

        }
    private val mControllerCallback = object : MediaControllerCompat.Callback() {
    }

    fun playPauseBuild() {
        val mediaController = MediaControllerCompat.getMediaController(this@PlayerActivity)
        btn.setOnClickListener {
            val state = mediaController.playbackState.state
            // if it is not playing then what are you waiting for ? PLAY !
            if (state == PlaybackStateCompat.STATE_PAUSED ||
                state == PlaybackStateCompat.STATE_STOPPED ||
                state == PlaybackStateCompat.STATE_NONE
            ) {

                mediaController.transportControls.playFromUri(
                    Uri.parse("asset:///heart_attack.mp3"),
                    null
                )
                btn.text = "Pause"
            }
            // you are playing ? knock it off !
            else if (state == PlaybackStateCompat.STATE_PLAYING ||
                state == PlaybackStateCompat.STATE_BUFFERING ||
                state == PlaybackStateCompat.STATE_CONNECTING
            ) {
                mediaController.transportControls.pause()
                btn.text = "Play"
            }
        }
        mediaController.registerCallback(mControllerCallback)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        val componentName = ComponentName(this, MusicServiceClass::class.java)
        // initialize the browser
        mMediaBrowserCompat = MediaBrowserCompat(
            this, componentName, //Identifier for the service
            connectionCallback,
            null
        )

    }

    override fun onStart() {
        super.onStart()
        // connect the controllers again to the session
        // without this connect() you won't be able to start the service neither control it with the controller
        mMediaBrowserCompat.connect()
    }

    override fun onStop() {
        super.onStop()
        // Release the resources
        val controllerCompat = MediaControllerCompat.getMediaController(this)
        controllerCompat?.unregisterCallback(mControllerCallback)
        mMediaBrowserCompat.disconnect()
    }


}*/
