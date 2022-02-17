package com.example.gmcaching

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gmcaching.adapter.CommentListAdapter
import com.example.gmcaching.data.Comment
import com.example.gmcaching.databinding.CommentFragmentBinding
import com.example.gmcaching.model.SharedViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class CommentFragment : Fragment() {
    companion object {
        fun newInstance() = CommentFragment()
    }

    private var _binding: CommentFragmentBinding? = null//??
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var mAuth: FirebaseAuth
    private val sharedViewModel: SharedViewModel by viewModels {
        SharedViewModel.SharedViewModelFactory()
    }

    private lateinit var myDataset: ArrayList<Comment>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = CommentFragmentBinding.inflate(inflater, container, false)//??
        val view = binding.root

        return view
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val cacheid = arguments?.getString("cacheid")
        mAuth = FirebaseAuth.getInstance()

        myDataset = ArrayList()


        val adapter = CommentListAdapter(myDataset)
        recyclerView = binding.recyclerviewComment
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        loadDataFromServer(cacheid!!)




        binding.buttonSaveComment.setOnClickListener {
            if (binding.editComment.text.isNotEmpty()){
            handleData()}
            else
                Toast.makeText(
                    this.requireContext(),
                    "Kommentar Leer",
                    Toast.LENGTH_SHORT
                ).show()

        }

    }

    private fun handleData() {
        val name = arguments?.let { it.getString("title").toString() }
        val id = arguments?.getString("cacheid")
        if (id != "-1") {
            val comment = Comment(
                cachename = name!!,
                cacheid = id!!,
                comment = binding.editComment.text.toString()
            )
            mAuth.currentUser?.let { user ->
                comment.creatorname = user.displayName

            }
            if (comment.cacheid != "-1" && comment.cachename != "null") {
                sharedViewModel.insertComment(comment)
                binding.editComment.text.clear()

            } else Toast.makeText(
                this.requireContext(),
                R.string.locationError,
                Toast.LENGTH_LONG
            ).show()

        } else Toast.makeText(
            this.requireContext(),
            R.string.locationError,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun loadDataFromServer(cacheid: String) {
        val databaseRef =
            FirebaseDatabase.getInstance("https://real-gm-caching-97159-default-rtdb.europe-west1.firebasedatabase.app").reference
        databaseRef.child("Comment").addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                myDataset.clear()
                for (postSnapshot in snapshot.children) {
                    val currentComment = postSnapshot.getValue(Comment::class.java)
                    if (currentComment?.cacheid == cacheid)
                        myDataset.add(currentComment)
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