package com.example.gmcaching

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gmcaching.adapter.CommentListAdapter
import com.example.gmcaching.adapter.ItemListAdapter
import com.example.gmcaching.data.Comment
import com.example.gmcaching.data.Item
import com.example.gmcaching.databinding.CommentFragmentBinding


class CommentFragment : Fragment() {
    companion object {
        fun newInstance() = CommentFragment()
    }
    private var _binding : CommentFragmentBinding?= null//??
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private val itemViewModel: ItemViewModel by viewModels {
        ItemViewModel.WordViewModelFactory((this.requireActivity().application as ItemApplication).repository)
    }

    private lateinit var myDataset : LiveData<List<Comment>>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = CommentFragmentBinding.inflate(inflater, container, false)//??
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myDataset=itemViewModel.allComments

        val adapter = CommentListAdapter(this.requireContext(),myDataset)
        recyclerView = binding.recyclerviewComment
        recyclerView.adapter=adapter
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())



        itemViewModel.allComments.observe(this.requireActivity()) { Comments ->
            // Update the cached copy of the words in the adapter.
            Comments.let { adapter.submitList(it) }


        }

        binding.buttonSaveComment.setOnClickListener() {
           handleData()
        }

    }

    private fun handleData() {
        val id = arguments?.let { it.getInt("id")}
        val name= arguments?.let { it.getString("title").toString() }
        if ( id!=-1 ) {

//            val thiscache = itemViewModel.findeItemByID(id!!).asLiveData()
//            val comment =  Comment(cachename = thiscache.value!!.cacheName, cacheID = thiscache.value!!.id, comment = binding.buttonSaveComment.toString())
            val comment =  Comment(cachename = name!!, cacheID = id!!, comment = binding.editComment.text.toString())
            if (comment != null&&comment.cacheID!=-1&&comment.cachename!="null") {
                itemViewModel.insertComment(comment)
                binding.editComment.text.clear()

            }
            else Toast.makeText(
                this.requireContext(),
                R.string.locationError,
                Toast.LENGTH_LONG
            ).show()

        }
        else Toast.makeText(
            this.requireContext(),
            R.string.locationError,
            Toast.LENGTH_LONG
        ).show()
    }



}