package com.example.gmcaching

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gmcaching.adapter.ItemAdapter
import com.example.gmcaching.adapter.ItemListAdapter
import com.example.gmcaching.data.Item
import com.example.gmcaching.databinding.DatabaseFragmentBinding
import com.example.gmcaching.model.Cache
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnCompleteListener
import kotlin.properties.Delegates


class DatabaseFragment : Fragment() {
    companion object {
        fun newInstance() = DatabaseFragment()
    }
    private var _binding : DatabaseFragmentBinding?= null//??
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private val newCacheActivityRequestCode = 1
    private val itemViewModel: ItemViewModel by viewModels {
        WordViewModelFactory((this.requireActivity().application as ItemApplication).repository)
    }

    private var newLat :Double =0.0
    private var newLng :Double =0.0
    private lateinit var newTitle: String
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback


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


        recyclerView = binding.recyclerview
        val adapter = ItemListAdapter()
        recyclerView.adapter=adapter
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())




        itemViewModel.allItems.observe(this.requireActivity()) { Items ->
            // Update the cached copy of the words in the adapter.
            Items.let { adapter.submitList(it) }

        }
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
            val item = Item(cacheName = newTitle, lat = newLat, lng = newLng)
            itemViewModel.insert(item)
                }

        } else {
            Toast.makeText(
                this.requireContext(),
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }



}