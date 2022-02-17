package com.example.gmcaching.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.gmcaching.data.Cache
import com.example.gmcaching.data.CacheDao
import com.example.gmcaching.data.Comment
import com.example.gmcaching.data.CommentDao
import kotlinx.coroutines.launch


class SharedViewModel : ViewModel() {

    private val cacheDao = CacheDao()
    private val commentDao = CommentDao()

    fun insertItem(cache: Cache) {
        viewModelScope.launch {
            cacheDao.insert(cache)
        }

    }

    fun insertComment(comment: Comment) = viewModelScope.launch {
        commentDao.insert(comment)
    }

    fun updateCache(cache: Cache) = viewModelScope.launch {
        cacheDao.update(cache)
    }


    class SharedViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SharedViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}