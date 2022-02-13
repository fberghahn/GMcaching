package com.example.gmcaching.data

import android.content.Context
import android.widget.Toast
import androidx.annotation.WorkerThread
import com.example.gmcaching.R
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow

class CacheDao (){
    private lateinit var database: DatabaseReference
    private var isInsertSuccess =false

    fun getAlphabetizedWords(): Task<DataSnapshot> {
        return database.get()
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(item : Item): Boolean {

       val database = FirebaseDatabase.getInstance("https://real-gm-caching-97159-default-rtdb.europe-west1.firebasedatabase.app").reference
        val cache=Cache(database.push().key!!,"noch nicht implementiert",item.cacheName,item.lat,item.lng)
        database.child("Cache").child(cache.cacheid).setValue(cache).addOnCompleteListener{
            isInsertSuccess=true
//            Toast.makeText(
//                context,
//                "Erfolgreich hinzugefügt",
//                Toast.LENGTH_LONG
//            ).show()

        }.addOnFailureListener{
            isInsertSuccess=false

//            Toast.makeText(
//                context,
//                "nicht hinzugefügt",
//                Toast.LENGTH_LONG
//            ).show()
        }
        return isInsertSuccess

//        database.child("Caches").child(cache.id.toString()).setValue(cache).addOnCompleteListener{
////            Toast.makeText(
////                context,
////                "Erfolgreich hinzugefügt",
////                Toast.LENGTH_LONG
////            ).show()
//
//        }.addOnFailureListener{
//
////            Toast.makeText(
////                context,
////                "nicht hinzugefügt",
////                Toast.LENGTH_LONG
////            ).show()
//        }
    }

    suspend fun deleteAll() {
        database.removeValue()
    }
}