package com.example.galaxyplayerkotlin.Adapters

import android.content.Intent
import android.media.MediaMetadataRetriever
import android.net.Uri
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

        val musicUri = song.path

        val mmr = MediaMetadataRetriever()
        mmr.setDataSource(viewHolder.song_cover.context, Uri.parse(musicUri))

        val rawArt = mmr.embeddedPicture

        Glide.with(viewHolder.song_cover.context)
            .load(rawArt)
            .into(viewHolder.song_cover)

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