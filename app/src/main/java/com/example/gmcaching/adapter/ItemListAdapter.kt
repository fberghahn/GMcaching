package com.example.gmcaching.adapter

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gmcaching.DatabaseFragmentDirections
import com.example.gmcaching.R
import com.example.gmcaching.R.drawable.image1
import com.example.gmcaching.data.Datasource
import com.example.gmcaching.data.Item

class ItemListAdapter(private val context: Context, private val dataset: LiveData<List<Item>>) : ListAdapter<Item, ItemListAdapter.WordViewHolder>(WordsComparator()) {
    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val button = itemView.findViewById<Button>(R.id.button1)
         val wordItemView: TextView = itemView.findViewById(R.id.item_title)
         val imageItemView: ImageView = itemView.findViewById(R.id.item_image)
        fun bind(text: String?) {
            wordItemView.text = text
        }
        fun bindimg(Int: Int) {
            imageItemView.setImageResource( image1)
        }

        companion object {
            fun create(parent: ViewGroup): WordViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return WordViewHolder(view)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.cacheName)
        holder.bindimg(image1)
        holder.imageItemView.setOnClickListener{
            val action = DatabaseFragmentDirections.actionDatabaseFragmentToMapsFragment(lat = current.lat.toString(), lng = current.lng.toString(), current.cacheName )
            holder.itemView.findNavController().navigate(action)
        }
        holder.button.setOnClickListener{
            val action = DatabaseFragmentDirections.actionDatabaseFragmentToMapsFragment(lat = current.lat.toString(), lng = current.lng.toString(), current.cacheName )
            holder.itemView.findNavController().navigate(action)
        }
    }



    class WordsComparator : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.cacheName == newItem.cacheName
        }
    }
}
