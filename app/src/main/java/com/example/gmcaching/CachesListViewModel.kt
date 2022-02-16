package com.example.gmcaching

import androidx.lifecycle.*
import com.example.gmcaching.data.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch



class ItemViewModel() : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
//    val allItems: LiveData<List<Item>> = itemRepository.allItems.asLiveData()

    val cacheDao = CacheDao()
    val commentDao = CommentDao()
   // val allItems: ArrayList<Cache> = cacheDao.getAlphabetizedWords()    Not Working

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insertItem(cache: Cache){
        viewModelScope.launch {
//        itemRepository.insert_item(item)
            cacheDao.insert(cache)
    }

    }

    fun insertComment(comment: Comment) = viewModelScope.launch {
        commentDao.insert(comment)
    }
    fun updateCache(cache: Cache) = viewModelScope.launch {
        cacheDao.update(cache)
    }

    fun setCacheFoundTrue(cacheid : String) = viewModelScope.launch {
        cacheDao.setFoundTrue(cacheid)
    }

    fun deleteAllItems() = viewModelScope.launch {
        cacheDao.deleteAll()
    }

    fun deleteAllComments() = viewModelScope.launch {
        commentDao.deleteAll()
    }

    fun findItemByID(id: Int) {

    }

//    fun getCommentsForCacheID(id:Int): Flow<List<Comment>> {
//
//
//    }


    class WordViewModelFactory() : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ItemViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ItemViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}