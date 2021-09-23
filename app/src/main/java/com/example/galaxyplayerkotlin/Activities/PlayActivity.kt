package com.example.galaxyplayerkotlin.Activities


import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.galaxyplayerkotlin.R
import com.example.galaxyplayerkotlin.service.ServiceClass
import com.example.galaxyplayerkotlin.service.ServiceClass.VideoServiceBinder
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ui.PlayerView


class PlayActivity : AppCompatActivity() {
    var playerView: PlayerView? = null
    var exoPlayer: ExoPlayer? = null
    var playbackPosition: Long = 0
    var currentWindow = 0
    var playWhenReady = false
    var name: String? = null
    var value: String? = null
    val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val exoPlayerS: ExoPlayer?
            if (service is VideoServiceBinder) {
                exoPlayerS = service.exoPlayerInstance
                if (exoPlayerS == null) {
                    Log.i("nullCheck", "exoPLayerService is null")
                    return
                }
                Log.i("service-activity", "Service Connected successfully ")
                try {
                    exoPlayerS.playWhenReady = playWhenReady
                    exoPlayerS.seekTo(currentWindow, playbackPosition)
                    exoPlayer = exoPlayerS
                    playerView!!.player = exoPlayer
                } catch (e: Exception) {
                    Log.i("nullCheck", e.toString())
                }
            }
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Toast.makeText(this@PlayActivity, "Service Disconnected", Toast.LENGTH_SHORT).show()
            Log.i("service-activity", "Service Disconnected successfully ")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        playerView = findViewById(R.id.exoPlayerView)
        val extras = intent.extras
        if (extras != null) {
            value = extras.getString("key")
            name = extras.getString("title")
        }
        startService(
            ServiceClass.createIntent(
                this,
                ServiceClass.ACTION_PLAY,
                value,
                name
            )
        )
    }

    public override fun onStart() {
        super.onStart()
        bindService(Intent(this, ServiceClass::class.java), connection, BIND_AUTO_CREATE)
        hideSystemUi()
    }

    public override fun onResume() {
        super.onResume()
        hideSystemUi()
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        playerView!!.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

    public override fun onStop() {
        unbindService(connection)
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer = null
        playerView!!.player = null
    }
}