package com.example.galaxyplayer.Adapters;

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
import com.example.galaxyplayer.Activities.MainActivityClass;
import com.example.galaxyplayer.Objects.MusicModel;
import com.example.galaxyplayer.R;

import java.util.List;

import static com.example.galaxyplayer.Activities.MainActivityClass.PLAY_FRAGMENT;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

     private List<MusicModel> songs;
     RecyclerAdapterListener recyclerAdapterListener;

     public RecyclerViewAdapter(List<MusicModel> songs) {
        this.songs = songs;
    }

    public interface RecyclerAdapterListener{
         void sendSongPath(String songPath);
         void sendSongTitle(String songTitle);
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


        recyclerAdapterListener.sendSongPath(song.getPath());
        recyclerAdapterListener.sendSongTitle(song.getTitle());

        MainActivityClass mainActivityClass = new MainActivityClass();

        mainActivityClass.goToFragment(PLAY_FRAGMENT);


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
