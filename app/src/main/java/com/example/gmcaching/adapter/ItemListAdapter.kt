package com.example.gmcaching.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gmcaching.DatabaseFragmentDirections
import com.example.gmcaching.R
import com.example.gmcaching.R.drawable.image1
import com.example.gmcaching.data.Cache

class ItemListAdapter( private val dataset: ArrayList<Cache>) :
    RecyclerView.Adapter<ItemListAdapter.CacheViewHolder>() {
    class CacheViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val showOnMapButton = itemView.findViewById<Button>(R.id.button_showOnMap)
        val commentButton = itemView.findViewById<ImageButton>(R.id.button_comment)
         val wordItemView: TextView = itemView.findViewById(R.id.item_title)
         val imageItemView: ImageView = itemView.findViewById(R.id.item_image)
        fun bind(text: String?) {
            wordItemView.text = text
        }
        fun bindimg(Int: Int) {
            imageItemView.setImageResource( image1)
        }

        companion object {
            fun create(parent: ViewGroup): CacheViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return CacheViewHolder(view)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CacheViewHolder {
        return CacheViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CacheViewHolder, position: Int) {
        val current = dataset[position]
        holder.bind(current.cacheName)
        holder.bindimg(image1)
        holder.imageItemView.setOnClickListener{
            val action = DatabaseFragmentDirections.actionDatabaseFragmentToMapsFragment(lat = current.lat.toString(), lng = current.lng.toString(), current.cacheName!! )
            holder.itemView.findNavController().navigate(action)
        }
        holder.showOnMapButton.setOnClickListener{
            val action = DatabaseFragmentDirections.actionDatabaseFragmentToMapsFragment(lat = current.lat.toString(), lng = current.lng.toString(), current.cacheName!! )
            holder.itemView.findNavController().navigate(action)
        }
        holder.commentButton.setOnClickListener{
            val action = DatabaseFragmentDirections.actionDatabaseFragmentToCommentFragment(current.cacheid, current.cacheName!!)
            holder.itemView.findNavController().navigate(action)
        }
    }



    class WordsComparator : DiffUtil.ItemCallback<Cache>() {
        override fun areItemsTheSame(oldItem: Cache, newItem: Cache): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Cache, newItem: Cache): Boolean {
            return oldItem.cacheName == newItem.cacheName && oldItem.creatorid == newItem.creatorid && oldItem.cacheid == newItem.cacheid
        }
    }

    override fun getItemCount(): Int {
       return dataset.size
    }
}
