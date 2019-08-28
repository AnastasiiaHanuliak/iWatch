package com.ahanuliak.iwatch.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
data class Movie(
    @PrimaryKey @ColumnInfo(name = "title") val title: String,
                @ColumnInfo(name = "release_year") val releaseYear: String,
                @ColumnInfo(name = "img") val img: String)
