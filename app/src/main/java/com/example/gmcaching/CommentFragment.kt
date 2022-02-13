package com.example.gmcaching

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gmcaching.adapter.CommentListAdapter
import com.example.gmcaching.data.Cache
import com.example.gmcaching.data.Comment
import com.example.gmcaching.databinding.CommentFragmentBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class CommentFragment : Fragment() {
    companion object {
        fun newInstance() = CommentFragment()
    }
    private var _binding : CommentFragmentBinding?= null//??
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private val itemViewModel: ItemViewModel by viewModels {
        ItemViewModel.WordViewModelFactory()
    }

    private lateinit var myDataset : ArrayList<Comment>



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

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val cacheid = arguments?.let { it.getString("cacheid")}

        myDataset=ArrayList<Comment>()


        val adapter = CommentListAdapter(myDataset)
        recyclerView = binding.recyclerviewComment
        recyclerView.adapter=adapter
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        loadDataFromServer(cacheid!!)




        binding.buttonSaveComment.setOnClickListener() {
           handleData()
        }

    }

    private fun handleData() {
        val name= arguments?.let { it.getString("title").toString() }
        val id = arguments?.let { it.getString("cacheid")}
        if ( id!="-1" ) {

//            val thiscache = itemViewModel.findeItemByID(id!!).asLiveData()
//            val comment =  Comment(cachename = thiscache.value!!.cacheName, cacheID = thiscache.value!!.id, comment = binding.buttonSaveComment.toString())
            val comment =  Comment(cachename = name!!, cacheid = id!!, comment = binding.editComment.text.toString())
            if (comment != null&&comment.cacheid!="-1"&&comment.cachename!="null") {
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

    private fun loadDataFromServer(cacheid:String) {
        val databaseRef = FirebaseDatabase.getInstance("https://real-gm-caching-97159-default-rtdb.europe-west1.firebasedatabase.app").reference
        databaseRef.child("Comment").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                myDataset.clear()
                for (postSnapshot in snapshot.children)
                {
                    val currentComment=postSnapshot.getValue(Comment::class.java)
                    if (currentComment?.cacheid==cacheid)
                    myDataset.add(currentComment!!)
                }
                Log.d("DatabaseRead", "Value is: $myDataset")
                recyclerView.adapter?.notifyDataSetChanged()
            }


            override fun onCancelled(error: DatabaseError) {
                Log.w("DatabaseRead", "Failed to read value.", error.toException())

            }

        })
    }

}