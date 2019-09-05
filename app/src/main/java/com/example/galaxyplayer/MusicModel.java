package com.example.galaxyplayer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import java.net.URI;


public class MusicModel {

    private String path;

    private String title;

   /* private String art;


     public String getArt() {
     return art;
     }

     public void setArt(String art) {
     this.art = art ;
     }*/

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

    @NonNull
    @Override
    public String toString() {
        return "name : " + title + " path : " + path;
    }
}
