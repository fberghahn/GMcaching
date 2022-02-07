package com.example.gmcaching

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gmcaching.adapter.ItemListAdapter
import com.example.gmcaching.data.Item
import com.example.gmcaching.data.ItemDao
import com.google.android.material.floatingactionbutton.FloatingActionButton



class DatabaseActivity : AppCompatActivity() {
    private val newCacheActivityRequestCode = 1
    private val ItemViewModel: ItemViewModel by viewModels {
        WordViewModelFactory((application as ItemApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = ItemListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        ItemViewModel.allItems.observe(this) { Items ->
            // Update the cached copy of the words in the adapter.
            Items.let { adapter.submitList(it) }
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@DatabaseActivity, AddCacheActivity::class.java)
            startActivityForResult(intent, newCacheActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newCacheActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.getStringExtra(AddCacheActivity.EXTRA_REPLY)?.let { reply ->
                val item = Item(cacheName = reply,koordinaten = 0.5,suchcounter = 3)
                ItemViewModel.insert(item)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
