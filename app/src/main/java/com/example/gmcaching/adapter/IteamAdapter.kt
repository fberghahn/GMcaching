package com.example.gmcaching.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.gmcaching.CachesListFragmentDirections
import com.example.gmcaching.R
import com.example.gmcaching.model.Cache

class ItemAdapter(private val context: Context, private val dataset: List<Cache>): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val button = view.findViewById<Button>(R.id.button1)
        val textView: TextView = view.findViewById(R.id.item_title)
        val imageView: ImageView = view.findViewById(R.id.item_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView.text = context.resources.getString(item.stringResourceId)
        holder.imageView.setImageResource(item.imageResourceId)
        holder.imageView.setOnClickListener{
            val action = CachesListFragmentDirections.actionCachesListFragmentToMapsActivity2(latlng = item.location.toString())
            holder.view.findNavController().navigate(action)
        }
        holder.button.setOnClickListener{
            val action = CachesListFragmentDirections.actionCachesListFragmentToMapsActivity2(latlng = item.location.toString())
            holder.view.findNavController().navigate(action)
        }
        holder.imageView.setOnClickListener{
            val action = CachesListFragmentDirections.actionCachesListFragmentToMapsActivity2()
            holder.view.findNavController().navigate(action)
        }
    }



    override fun getItemCount(): Int {
        return dataset.size
    }


}
