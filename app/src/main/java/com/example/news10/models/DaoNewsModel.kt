package com.example.news10.models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_table")
data class DaoNewsModel(
    @ColumnInfo(name = "news_type", defaultValue = "type")
    var type:String = "type",
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "title", defaultValue = "No Title")
    val title:String,
    @ColumnInfo(name = "author", defaultValue = "Ankush")
    val author:String,
    @ColumnInfo(name = "description", defaultValue = "helloo des")
    val description:String,
    @ColumnInfo(name = "url", defaultValue = "url def")
    var url:String = "no",
    @ColumnInfo(name = "url_image", defaultValue = "img")
    var urlToImage:String = "no",
    @ColumnInfo(name = "time", defaultValue = "00:00")
    val publishedAt:String ,
    @ColumnInfo(name = "date")
    var date: Long = 10)