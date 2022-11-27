package com.example.nasa.databse

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_table")
data class FavDataEntity(
    @PrimaryKey
    @ColumnInfo(name = "img_date") val imageDate: String,
    @ColumnInfo(name = "img_title") val imageTitle: String,
    @ColumnInfo(name = "img_thumbnail_url") val imageUrl: String,
    @ColumnInfo(name = "img_explanation") val imageExplanation: String,
    @ColumnInfo(name = "video_link") val videoLinkUrl: String,
    @ColumnInfo(name = "media_type") val mediaType: String
)