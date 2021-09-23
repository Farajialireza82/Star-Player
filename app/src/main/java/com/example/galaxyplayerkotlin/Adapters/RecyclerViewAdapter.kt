package com.example.galaxyplayerkotlin.Adapters

import android.content.ContentResolver
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.media.ThumbnailUtils
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.galaxyplayerkotlin.Activities.PlayActivity
import com.example.galaxyplayerkotlin.Objects.MusicModel
import com.example.galaxyplayerkotlin.R
import java.io.FileNotFoundException
import kotlin.math.log
import java.io.FileDescriptor

import android.os.ParcelFileDescriptor

import android.content.ContentUris

import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import kotlin.Exception
import androidx.core.content.FileProvider

import java.io.File

import android.content.Context
import android.util.Size
import java.security.AccessController.getContext


class RecyclerViewAdapter(private val songs: List<MusicModel>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recycler_view_holder, viewGroup, false)
    )


    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {

        val song = songs[i]

        val title = song.title

        viewHolder.song_name.text = title

        viewHolder.artist_name.text = song.artist

        viewHolder.song_duration.text = song.duration

            Log.i("music uri: ", song.path)

        val musicUri = song.path

        val mmr = MediaMetadataRetriever()
        mmr.setDataSource(viewHolder.song_cover.context, Uri.parse(musicUri))

        val rawArt = mmr.embeddedPicture


        if(rawArt.toString()== "null"){
            Glide.with(viewHolder.song_cover.context)
                .load(R.mipmap.icon)
                .into(viewHolder.song_cover)

        }else{
            Glide.with(viewHolder.song_cover.context)
                .load(rawArt)
                .into(viewHolder.song_cover)

        }


        viewHolder.itemView.setOnClickListener {
            set(song, viewHolder)
        }
    }

    override fun getItemCount() = songs.size

    private operator fun set(song: MusicModel, viewHolder: ViewHolder) {
        val intent = Intent(viewHolder.song_name.context, PlayActivity::class.java)
        intent.putExtra("key", song.path)
        intent.putExtra("name", song.title)
        viewHolder.song_cover.context.startActivity(intent)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var song_name: TextView = itemView.findViewById(R.id.song_name)
        var artist_name: TextView = itemView.findViewById(R.id.artist_name)
        var song_duration: TextView = itemView.findViewById(R.id.song_duration)
        var song_cover: ImageView = itemView.findViewById(R.id.music_cover)
    }
}