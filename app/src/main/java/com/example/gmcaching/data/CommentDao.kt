package com.example.gmcaching.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
@Dao
interface CommentDao {


        @Query("SELECT * FROM comment ORDER BY id ASC")
        fun getAlphabetizedWords(): Flow<List<Comment>>

        @Query("SELECT * FROM comment where cacheID = :id ORDER BY id ASC")
        fun getCommentsForCacheID(id:Int): Flow<List<Comment>>

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        suspend fun insert(Comment: Comment)

        @Query("DELETE FROM comment")
        suspend fun deleteAll()

}