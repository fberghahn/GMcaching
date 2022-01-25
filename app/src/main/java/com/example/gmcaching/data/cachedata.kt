package com.example.gmcaching.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class Item (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val cacheName: String,
    @ColumnInfo(name = "koordinaten")
    val koordinaten: Double,
    @ColumnInfo(name = "suchcounter")
    val suchcounter: Int,

    )