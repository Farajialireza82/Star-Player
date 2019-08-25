package com.example.galaxyplayer;

import android.graphics.Bitmap;


public class MusicModel {

    private String path;

    private String title;

    private Bitmap art;


    public Bitmap getArt() {
        return art;
    }

    public void setArt(Bitmap art) {
        this.art = art;
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
