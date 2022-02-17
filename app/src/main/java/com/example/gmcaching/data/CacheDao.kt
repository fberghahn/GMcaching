package com.example.gmcaching.data

import androidx.annotation.WorkerThread
import com.google.firebase.database.FirebaseDatabase


class CacheDao {
    private val databaseRef =
        FirebaseDatabase.getInstance("https://real-gm-caching-97159-default-rtdb.europe-west1.firebasedatabase.app").reference
    private var isInsertSuccess = false

    @WorkerThread
    fun insert(cache: Cache) {


        val value = Cache(
            databaseRef.push().key!!,
            cache.creatorid,
            cache.cacheName,
            cache.lat,
            cache.lng,
            cache.image
        )
        databaseRef.child("Cache").child(value.cacheid).setValue(value).addOnCompleteListener {
            isInsertSuccess = true

        }.addOnFailureListener {
            isInsertSuccess = false

        }

    }

    @WorkerThread
    fun update(cache: Cache) {
        databaseRef.child("Cache").child(cache.cacheid).setValue(cache).addOnCompleteListener {
            isInsertSuccess = true

        }.addOnFailureListener {
            isInsertSuccess = false

        }

    }


}