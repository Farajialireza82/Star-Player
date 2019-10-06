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
import com.example.galaxyplayerkotlin.Activities.MainActivityClass;
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

        viewHolder.textView.setText(title);

        String musicUri = song.getPath();

        MediaMetadataRetriever mmr = new MediaMetadataRetriever();

        mmr.setDataSource(viewHolder.imageView.getContext() , Uri.parse(musicUri));

        byte[] rawArt = mmr.getEmbeddedPicture();

        Glide.with(viewHolder.imageView.getContext())
                .load(rawArt)
                .into(viewHolder.imageView);

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

        Intent intent = new Intent(viewHolder.textView.getContext() , PlayActivity.class);

        intent.putExtra("key" , song.getPath());
        intent.putExtra("name" , song.getTitle());

        viewHolder.imageView.getContext().startActivity(intent);

    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.song_url);
            imageView = itemView.findViewById(R.id.music_cover);
        }
    }
}

