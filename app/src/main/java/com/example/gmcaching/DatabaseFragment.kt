package com.example.gmcaching

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gmcaching.adapter.ItemListAdapter
import com.example.gmcaching.data.Cache
import com.example.gmcaching.databinding.DatabaseFragmentBinding
import com.google.android.gms.location.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.collections.ArrayList


class DatabaseFragment : Fragment() {
    companion object {
        fun newInstance() = DatabaseFragment()
    }
    private var _binding : DatabaseFragmentBinding?= null//??
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var myDataset : ArrayList<Cache>
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DatabaseFragmentBinding.inflate(inflater, container, false)//??
        val view = binding.root


        mAuth = FirebaseAuth.getInstance( )
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.requireActivity())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myDataset=ArrayList<Cache>()





        val adapter = ItemListAdapter(this.requireContext(),myDataset)
        recyclerView = binding.recyclerview
        recyclerView.adapter=adapter
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())

        loadDataFromServer()

        binding.logoutButton.setOnClickListener{
            mAuth.signOut()
            val intent = Intent(this.requireActivity(), Login::class.java )
            startActivity(intent)
        }



        binding.fab.setOnClickListener() {
            val action = DatabaseFragmentDirections.actionDatabaseFragmentToAddCacheFragment()
            findNavController().navigate(action)
        }

    }






    private fun loadDataFromServer() {
        val databaseRef = FirebaseDatabase.getInstance("https://real-gm-caching-97159-default-rtdb.europe-west1.firebasedatabase.app").reference
        databaseRef.child("Cache").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                binding.progressBarInList.visibility=View.GONE
                binding.fab.visibility = View.VISIBLE
                binding.logoutButton.visibility= View.VISIBLE
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                myDataset.clear()

                for (postSnapshot in snapshot.children)
                {
                    val currentCache=postSnapshot.getValue(Cache::class.java)
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