package com.example.gmcaching.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gmcaching.DatabaseFragmentDirections
import com.example.gmcaching.R
import com.example.gmcaching.R.drawable.image1
import com.example.gmcaching.data.Cache
import com.google.firebase.storage.FirebaseStorage

class ItemListAdapter( private val context: Context ,private val dataset: ArrayList<Cache>) :
    RecyclerView.Adapter<ItemListAdapter.CacheViewHolder>() {
    class CacheViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val greylayout =itemView.findViewById<ImageView>(R.id.greyscheme)
        val showOnMapButton = itemView.findViewById<Button>(R.id.button_showOnMap)
        val commentButton = itemView.findViewById<ImageButton>(R.id.button_comment)
         val wordItemView: TextView = itemView.findViewById(R.id.item_title)
         val imageItemView: ImageView = itemView.findViewById(R.id.item_image)
        val imagenotfoundtextview : TextView=itemView.findViewById(R.id.imagenotfound)
        val progressbar  = itemView.findViewById(R.id.progressBar) as ProgressBar
        fun bind(text: String?) {
            wordItemView.text = text
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
        if (current.found)
        {
        holder.greylayout.visibility=View.VISIBLE
        }
        if (current.image!="null"){
            getImage(current.image, holder)

        }else{
            holder.imageItemView.setImageResource(image1)
            holder.progressbar.visibility = View.GONE
        }

        holder.imageItemView.setOnClickListener{
            val action = DatabaseFragmentDirections.actionDatabaseFragmentToMapsFragment(lat = current.lat.toString(), lng = current.lng.toString(), current.cacheName!!,current.cacheid!!, current.creatorid!!,current.image!!)
            holder.itemView.findNavController().navigate(action)
        }
        holder.showOnMapButton.setOnClickListener{
            val action = DatabaseFragmentDirections.actionDatabaseFragmentToMapsFragment(lat = current.lat.toString(), lng = current.lng.toString(), current.cacheName!!,current.cacheid!!,current.creatorid!!,current.image!! )
            holder.itemView.findNavController().navigate(action)
        }
        holder.commentButton.setOnClickListener{
            val action = DatabaseFragmentDirections.actionDatabaseFragmentToCommentFragment(current.cacheid, current.cacheName!!)
            holder.itemView.findNavController().navigate(action)
        }
    }

    private fun getImage(image: String?, holder: CacheViewHolder)  {
        val storage = FirebaseStorage.getInstance("gs://real-gm-caching-97159.appspot.com/").reference

        val pathReference = storage.child("images/")
        var usableImage: Bitmap =ContextCompat.getDrawable(context, image1)!!.toBitmap()

        pathReference.child(image!!).getBytes(1024*1024*3).addOnSuccessListener {
           holder.imageItemView.setImageBitmap( BitmapFactory.decodeByteArray(it,0,it.size))
            holder.progressbar.visibility = View.GONE
            holder.imagenotfoundtextview.visibility=View.GONE
           notifyDataSetChanged()

//           imagesave=ContextCompat.getDrawable(context, R.drawable.image2)!!.toBitmap()



        }.addOnFailureListener {
            holder.imageItemView.setImageBitmap(usableImage)
            holder.progressbar.visibility = View.GONE
            holder.imagenotfoundtextview.visibility=View.VISIBLE

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
