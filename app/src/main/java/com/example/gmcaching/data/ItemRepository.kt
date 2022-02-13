//package com.example.gmcaching.data
//
//import androidx.annotation.WorkerThread
//import kotlinx.coroutines.flow.Flow
//
//// Declares the DAO as a private property in the constructor. Pass in the DAO
//// instead of the whole database, because you only need access to the DAO
//class ItemRepository(private val itemDao: ItemDao, private val commentDao: CommentDao) {
//
//    // Room executes all queries on a separate thread.
//    // Observed Flow will notify the observer when the data has changed.
////    val allItems: Flow<List<Item>> = itemDao.getAlphabetizedWords()
////    val allComments: Flow<List<Comment>> = commentDao.getAlphabetizedWords()
//
//    // By default Room runs suspend queries off the main thread, therefore, we don't need to
//    // implement anything else to ensure we're not doing long running database work
//    // off the main thread.
//    @Suppress("RedundantSuspendModifier")
//    @WorkerThread
//    suspend fun insert_item(item: Item) {
//        itemDao.insert(item)
//    }
//    @WorkerThread
//    suspend fun deleteAll_items() {
//        itemDao.deleteAll()
//    }
//    @Suppress("RedundantSuspendModifier")
//    @WorkerThread
//    suspend fun insert_comment(comment: Comment) {
//        commentDao.insert(comment)
//    }
//    @WorkerThread
//    suspend fun deleteAll_comments() {
//        commentDao.deleteAll()
//    }
//    @WorkerThread
//     fun findeItemByID(id: Int) : Flow<Item> {
//        return itemDao.findItemByID(id)
//    }
//    @WorkerThread
//    fun getCommentsForCacheID(id:Int): Flow<List<Comment>> {
//       return commentDao.getCommentsForCacheID(id)
//    }
//
//}
