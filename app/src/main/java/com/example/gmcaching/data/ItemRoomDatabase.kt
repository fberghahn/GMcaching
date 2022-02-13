//package com.example.gmcaching.data
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import androidx.sqlite.db.SupportSQLiteDatabase
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.launch
//
//// Annotates class to be a Room Database with a table (entity) of the Word class
//@Database(entities = arrayOf(Item::class,Comment::class), version = 4, exportSchema = false)
//public abstract class ItemRoomDatabase : RoomDatabase() {
//
//    abstract fun itemDao(): ItemDao
//    abstract fun commentDao(): CommentDao
//
//    private class ItemDatabaseCallback(
//        private val scope: CoroutineScope
//    ) : RoomDatabase.Callback() {
//
//        override fun onCreate(db: SupportSQLiteDatabase) {
//            super.onCreate(db)
//            INSTANCE?.let { database ->
//                scope.launch {
//                    populateDatabase(database.itemDao())
//
//
//
//                }
//            }
//        }
//
//        suspend fun populateDatabase(itemDao: ItemDao) {
//            // Delete all content here.
//            itemDao.deleteAll()
//
//
//            // Add sample words.
//            var name = Item(1,"Test1",4.4,1.2)
//            itemDao.insert(name)
//            name = Item(1,"Test2",5.4,2.0 )
//            itemDao.insert(name)
//
//
//
//        }
//        suspend fun populateCommentDatabase(commentDao: CommentDao) {
//            // Delete all content here.
//            commentDao.deleteAll()
//
//
//            // Add sample words.
//            var comment = Comment(1,1,"Test1","war echt gut versteckt")
//            commentDao.insert(comment)
//
//
//
//
//        }
//    }
//
//    companion object {
//        // Singleton prevents multiple instances of database opening at the
//        // same time.
//        @Volatile
//        private var INSTANCE: ItemRoomDatabase? = null
//        @Volatile
//        private var INSTANCE2: ItemRoomDatabase? = null
//
//        fun getDatabase(context: Context, scope: CoroutineScope): ItemRoomDatabase {
//            // if the INSTANCE is not null, then return it,
//            // if it is, then create the database
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    ItemRoomDatabase::class.java,
//                    "item_database"
//                )
//                    .addCallback(ItemDatabaseCallback(scope)).
//                fallbackToDestructiveMigration().build() //Datenbank wird gelöscht wenn wir anzeigen ändern
//                INSTANCE = instance
//                // return instance
//                instance
//
//
//            }
//        }
//
//
//    }
//}
