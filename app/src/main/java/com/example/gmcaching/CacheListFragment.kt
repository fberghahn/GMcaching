package com.example.gmcaching

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gmcaching.adapter.CacheListAdapter
import com.example.gmcaching.data.Cache
import com.example.gmcaching.databinding.CacheListFragmentBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class CacheListFragment : Fragment() {
    companion object {
        fun newInstance() = CacheListFragment()
    }

    private var _binding: CacheListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var myDataset: ArrayList<Cache>
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CacheListFragmentBinding.inflate(inflater, container, false)//??
        val view = binding.root


        mAuth = FirebaseAuth.getInstance()
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this.requireActivity())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myDataset = ArrayList()


        val adapter = CacheListAdapter(this.requireContext(), myDataset)
        recyclerView = binding.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())

        loadDataFromServer()

        binding.logoutButton.setOnClickListener {
            mAuth.signOut()
            val intent = Intent(this.requireActivity(), Login::class.java)
            startActivity(intent)
        }



        binding.fab.setOnClickListener {
            val action = CacheListFragmentDirections.actionCacheListFragmentToAddCacheFragment()
            findNavController().navigate(action)
        }

    }


    private fun loadDataFromServer() {
        val databaseRef =
            FirebaseDatabase.getInstance("https://real-gm-caching-97159-default-rtdb.europe-west1.firebasedatabase.app").reference

        // Wird einmal aufgerufen, und dann immer wieder wenn daten an diesem Ort aktualisiert werden
        databaseRef.child("Cache").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                binding.progressBarInList.visibility = View.GONE
                binding.fab.visibility = View.VISIBLE
                binding.logoutButton.visibility = View.VISIBLE
                myDataset.clear()

                for (postSnapshot in snapshot.children) {
                    val currentCache = postSnapshot.getValue(Cache::class.java)
                    myDataset.add(currentCache!!)

                }
                myDataset.sortBy { cache: Cache -> cache.found }
                Log.d("DatabaseRead", "Value is: $myDataset")
                recyclerView.adapter?.notifyDataSetChanged()
            }


            override fun onCancelled(error: DatabaseError) {
                Log.w("DatabaseRead", "Failed to read value.", error.toException())


            }

        })
    }


}