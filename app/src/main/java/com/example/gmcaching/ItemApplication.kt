package com.example.gmcaching

import android.app.Application
import com.example.gmcaching.data.ItemRepository
import com.example.gmcaching.data.ItemRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob


class ItemApplication : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { ItemRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { ItemRepository(database.itemDao(), database.commentDao()) }
}

