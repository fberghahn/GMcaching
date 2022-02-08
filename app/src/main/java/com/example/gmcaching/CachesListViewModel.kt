package com.example.gmcaching

import androidx.lifecycle.*
import com.example.gmcaching.data.Item
import com.example.gmcaching.data.ItemRepository
import kotlinx.coroutines.launch

class ItemViewModel(private val repository: ItemRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allItems: LiveData<List<Item>> = repository.allItems.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(item: Item) = viewModelScope.launch {
        repository.insert(item)
    }
    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }
}

class WordViewModelFactory(private val repository: ItemRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ItemViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
