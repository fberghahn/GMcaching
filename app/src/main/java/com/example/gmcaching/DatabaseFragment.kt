package com.example.gmcaching

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gmcaching.adapter.ItemListAdapter
import com.example.gmcaching.data.Cache
import com.example.gmcaching.databinding.DatabaseFragmentBinding
import com.google.android.gms.location.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class DatabaseFragment : Fragment() {
    companion object {
        fun newInstance() = DatabaseFragment()
    }
    private var _binding : DatabaseFragmentBinding?= null//??
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private val itemViewModel: ItemViewModel by viewModels {
        ItemViewModel.WordViewModelFactory()
    }

    private var newLat :Double =0.0
    private var newLng :Double =0.0
    private lateinit var newTitle: String
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var myDataset : ArrayList<Cache>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DatabaseFragmentBinding.inflate(inflater, container, false)//??
        val view = binding.root

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.requireActivity())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myDataset=ArrayList<Cache>()



        val adapter = ItemListAdapter(myDataset)
        recyclerView = binding.recyclerview
        recyclerView.adapter=adapter
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        loadDataFromServer()


        handleData()
        binding.fab.setOnClickListener() {
            val action = DatabaseFragmentDirections.actionDatabaseFragmentToAddCacheFragment()
            findNavController().navigate(action)
        }

    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==100&& grantResults.isNotEmpty()&&(grantResults[0]+grantResults[1] == PackageManager.PERMISSION_GRANTED))
        {
            handleData()
        }
        else
        {
            Toast.makeText(
                this.requireContext(),
                R.string.locationError,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun handleData() {
        if (arguments?.let { it.getBoolean("text_is_ready")} == true ) {

            arguments?.let {
                newTitle=it.getString("name").toString()
                newLat= it.getString("lat")!!.toDouble()
                newLng= it.getString("lng")!!.toDouble()
            }
            if (newLat!=0.0 && newLng!=0.0){
            val cache = Cache( creatorid = "noch nicht implementiert", cacheName = newTitle, lat = newLat, lng = newLng)
            if(itemViewModel.insertItem(cache))
            {
                Toast.makeText(
                context,
                "Nicht hinzugefügt",
                Toast.LENGTH_LONG
            ).show()
            }
                else{
                Toast.makeText(
                    context,
                    "Erfolgreich hinzugefügt",
                    Toast.LENGTH_LONG
                ).show()
            }
                arguments?.clear()
                }

        }
    }

    private fun loadDataFromServer() {
        val databaseRef = FirebaseDatabase.getInstance("https://real-gm-caching-97159-default-rtdb.europe-west1.firebasedatabase.app").reference
        databaseRef.child("Cache").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                myDataset.clear()
                for (postSnapshot in snapshot.children)
                {
                    val currentCache=postSnapshot.getValue(Cache::class.java)
                    myDataset.add(currentCache!!)
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