package com.example.gmcaching.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gmcaching.R
import com.example.gmcaching.data.Comment

class CommentListAdapter ( private val dataset: ArrayList<Comment>) : RecyclerView.Adapter<CommentListAdapter.CommentViewHolder>() {
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
    ): CommentListAdapter.CommentViewHolder {
        return CommentListAdapter.CommentViewHolder.create(parent)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: CommentListAdapter.CommentViewHolder, position: Int) {
        val current = dataset[position]

            holder.bind(current.comment)
            holder.creatorItemView.text = current.creatorid


//            holder.itemView.visibility=View.GONE
            //Recyclerview resetten

    }




    class WordsComparator : DiffUtil.ItemCallback<Comment>() {
        override fun areItemsTheSame(oldComment: Comment, newComment: Comment): Boolean {
            return oldComment === newComment
        }

        override fun areContentsTheSame(oldComment: Comment, newComment: Comment): Boolean {
            return oldComment.comment == newComment.comment
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}