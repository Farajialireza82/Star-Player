package com.example.galaxyplayerkotlin.Adapters;

import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.galaxyplayerkotlin.Activities.PlayActivity;
import com.example.galaxyplayerkotlin.Objects.MusicModel;
import com.example.galaxyplayerkotlin.R;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<MusicModel> songs;

    public RecyclerViewAdapter(List<MusicModel> songs) {
        this.songs = songs;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.recycler_view_holder, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        final MusicModel song = songs.get(i);


        final String title = song.getTitle();

        viewHolder.song_name.setText(title);
        viewHolder.artist_name.setText(song.getArtist());
        viewHolder.song_duration.setText(song.getDuration());

        String musicUri = song.getPath();

        MediaMetadataRetriever mmr = new MediaMetadataRetriever();

        mmr.setDataSource(viewHolder.song_cover.getContext() , Uri.parse(musicUri));

        byte[] rawArt = mmr.getEmbeddedPicture();

        Glide.with(viewHolder.song_cover.getContext())
                .load(rawArt)
                .into(viewHolder.song_cover);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                set(song , viewHolder );


            }
        });

    }

    @Override
    public int getItemCount() {
        return songs.size();
    }


    private void set(MusicModel song, RecyclerViewAdapter.ViewHolder viewHolder) {

        Intent intent = new Intent(viewHolder.song_name.getContext() , PlayActivity.class);

        intent.putExtra("key" , song.getPath());
        intent.putExtra("name" , song.getTitle());

        viewHolder.song_cover.getContext().startActivity(intent);

    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView song_name;
        TextView artist_name;
        TextView song_duration;
        ImageView song_cover;



        ViewHolder(@NonNull View itemView) {
            super(itemView);

            song_name = itemView.findViewById(R.id.song_name);
            song_cover = itemView.findViewById(R.id.music_cover);
            artist_name = itemView.findViewById(R.id.artist_name);
            song_duration = itemView.findViewById(R.id.song_duration);

        }
    }
}