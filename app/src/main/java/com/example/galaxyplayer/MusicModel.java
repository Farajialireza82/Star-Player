package com.example.galaxyplayer;

import androidx.annotation.NonNull;



public class MusicModel {

    private String path;

    private String title;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

     String getTitle() {
        return title;
    }

     void setTitle(String title) {
        this.title = title;
    }

    @NonNull
    @Override
    public String toString() {
        return "name : " + title + " path : " + path;
    }
}
