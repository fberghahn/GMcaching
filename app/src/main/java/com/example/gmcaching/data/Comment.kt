package com.example.gmcaching.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comment")
data class Comment (


    @PrimaryKey(autoGenerate = true)
    val id: Int =0 ,
    @ColumnInfo(name = "cacheID")
    val cacheID: Int,
    @ColumnInfo(name = "Cache Name:")
    val cachename: String,
    @ColumnInfo(name = "Kommentar")
    val comment: String,

    )
