package com.example.galaxyplayerkotlin.Objects;

import androidx.annotation.NonNull;

public class MusicModel {

    private String path;

    private String title;

    private String artist;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    private String duration;

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

    public String getArtist() { return artist;}

    public void setArtist(String artist) { this.artist = artist;}

    @NonNull
    @Override
    public String toString() {
        return "name : " + title + " path : " + path;
    }
}