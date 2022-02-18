package com.example.gmcaching.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gmcaching.R
import com.example.gmcaching.data.Comment

class CommentListAdapter(private val dataset: ArrayList<Comment>) :
    RecyclerView.Adapter<CommentListAdapter.CommentViewHolder>() {
    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val commentItemView: TextView = itemView.findViewById(R.id.comment)
        val creatorItemView: TextView = itemView.findViewById(R.id.username)
        fun bind(text: String?) {
            commentItemView.text = text
        }


        companion object {
            fun create(parent: ViewGroup): CommentViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_comment, parent, false)
                return CommentViewHolder(view)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommentViewHolder {
        return CommentViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val current = dataset[position]

        holder.commentItemView.text=current.comment
        holder.creatorItemView.text = current.creatorname


    }


    override fun getItemCount(): Int {
        return dataset.size
    }
}