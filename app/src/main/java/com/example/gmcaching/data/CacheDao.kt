package com.example.gmcaching.data

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.gmcaching.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage


class CacheDao (){
    private  val databaseRef = FirebaseDatabase.getInstance("https://real-gm-caching-97159-default-rtdb.europe-west1.firebasedatabase.app").reference
    private var isInsertSuccess =false

    @WorkerThread
    fun getAlphabetizedWords(): ArrayList<Cache> {
        // Read from the database
        val cacheList  = ArrayList<Cache>()
        databaseRef.child("Cache").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                 cacheList.clear()
                for (postSnapshot in snapshot.children)
                {
                    val currentCache=postSnapshot.getValue(Cache::class.java)
                    cacheList.add(currentCache!!)
                }
                Log.d("DatabaseRead", "Value is: $cacheList")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("DatabaseRead", "Failed to read value.", error.toException())

            }

        })
        return cacheList
    }

    @WorkerThread
    suspend fun insert(cache : Cache): Boolean {


        val cache=Cache(databaseRef.push().key!!,cache.creatorid,cache.cacheName,cache.lat,cache.lng ,cache.image)
        databaseRef.child("Cache").child(cache.cacheid).setValue(cache).addOnCompleteListener{
            isInsertSuccess=true

        }.addOnFailureListener{
            isInsertSuccess=false

        }
        return isInsertSuccess

    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll() {
        databaseRef.removeValue()
    }

@WorkerThread
    suspend fun getImage(image: String?, context :Context): Bitmap {
        val storage = FirebaseStorage.getInstance().reference

        val pathReference = storage.child("images/")
        var useableImage: Bitmap = ContextCompat.getDrawable(context, R.drawable.image1)!!.toBitmap()
        pathReference.child(image!!).getBytes(1024*1024).addOnSuccessListener {
//            useableImage=BitmapFactory.decodeByteArray(it,0,it.size)
            useableImage= ContextCompat.getDrawable(context, R.drawable.image2)!!.toBitmap()

        }.addOnFailureListener {
            useableImage= ContextCompat.getDrawable(context, R.drawable.image2)!!.toBitmap()
        }
        return useableImage
    }
}