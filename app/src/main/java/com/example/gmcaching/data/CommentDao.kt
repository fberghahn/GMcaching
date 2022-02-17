package com.example.gmcaching.data

import androidx.annotation.WorkerThread
import com.google.firebase.database.FirebaseDatabase

class CommentDao {


    private val databaseRef =
        FirebaseDatabase.getInstance("https://real-gm-caching-97159-default-rtdb.europe-west1.firebasedatabase.app").reference
    private var isInsertSuccess = false

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(comment: Comment): Boolean {


        val value = Comment(
            databaseRef.push().key!!,
            comment.creatorname,
            comment.cacheid!!,
            comment.comment,
            comment.cachename
        )
        databaseRef.child("Comment").child(value.commentid).setValue(value)
            .addOnCompleteListener {
                isInsertSuccess = true

            }.addOnFailureListener {
            isInsertSuccess = false

        }
        return isInsertSuccess

    }
}