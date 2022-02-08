package com.example.gmcaching.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_table")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int =0 ,
    @ColumnInfo(name = "name")
    val cacheName: String,
    @ColumnInfo(name = "koordinaten: lat")
    val lat: Double,
    @ColumnInfo(name = "koordinaten: lng")
    val lng: Double,
    @ColumnInfo(name = "suchcounter")
    val suchcounter: Int,

    )