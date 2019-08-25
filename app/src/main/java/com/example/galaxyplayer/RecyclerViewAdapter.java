package com.example.galaxyplayer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<MusicModel> songs;

    private Integer num = 0;

    public RecyclerViewAdapter(List<MusicModel> songs) {
        this.songs = songs;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.recycler_view_holder, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {


        final MusicModel song = songs.get(i);

        final String title = song.getTitle();

        viewHolder.textView.setText(title);

        viewHolder.imageView.setImageBitmap(song.getArt());

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


    public void set(MusicModel song , RecyclerViewAdapter.ViewHolder viewHolder) {

        Context context = viewHolder.textView.getContext();

        Intent playActivity = new Intent(context, PlayActivity.class);

        playActivity.putExtra("key", song.getPath());

        playActivity.putExtra("title" , song.getTitle());

        if(num == 0 ) {

            context.startActivity(playActivity);
            num++;
        }
        else{

        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.song_url);
            imageView = itemView.findViewById(R.id.music_cover);
        }
    }
}

