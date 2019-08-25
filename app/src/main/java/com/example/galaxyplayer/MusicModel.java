package com.example.galaxyplayer;

import android.graphics.Bitmap;
import java.net.URI;


public class MusicModel {

    private String path;

    private String title;

    private URI art;


    public URI getArt() {
        return art;
    }

    public void setArt(URI art) {
        this.art = art ;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
