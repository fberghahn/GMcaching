package com.example.gmcaching.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gmcaching.R
import com.example.gmcaching.data.Comment
import com.example.gmcaching.data.Item

class CommentListAdapter (private val cacheid:Int, private val context: Context, private val dataset: LiveData<List<Comment>>) : ListAdapter<Comment, CommentListAdapter.WordViewHolder>(
    CommentListAdapter.WordsComparator()
) {
    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val commentItemView: TextView = itemView.findViewById(R.id.comment)
        fun bind(text: String?) {
            commentItemView.text = text
        }


        companion object {
            fun create(parent: ViewGroup): WordViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_comment, parent, false)
                return WordViewHolder(view)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommentListAdapter.WordViewHolder {
        return CommentListAdapter.WordViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CommentListAdapter.WordViewHolder, position: Int) {
        val current = getItem(position)
        if (current.cacheID==cacheid)
        holder.bind(current.comment)
       else
        {
            holder.itemView.visibility=View.GONE
            //Recyclerview resetten
        }
    }




    class WordsComparator : DiffUtil.ItemCallback<Comment>() {
        override fun areItemsTheSame(oldComment: Comment, newComment: Comment): Boolean {
            return oldComment === newComment
        }

        override fun areContentsTheSame(oldComment: Comment, newComment: Comment): Boolean {
            return oldComment.comment == newComment.comment
        }
    }
}