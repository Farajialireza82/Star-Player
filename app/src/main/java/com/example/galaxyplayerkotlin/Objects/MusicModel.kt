package com.example.galaxyplayerkotlin.Objects

data class MusicModel(
    var path: String,
    var title: String,
    var artist: String,
    var duration: String
) {


    override fun toString(): String {
        return "name : $title path : $path"
    }
}