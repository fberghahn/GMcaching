package com.example.gmcaching.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Query("SELECT * FROM item_table ORDER BY id ASC")
    fun getAlphabetizedWords(): Flow<List<Item>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(Item: Item)

    @Query("DELETE FROM item_table")
    suspend fun deleteAll()
}

