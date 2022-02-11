package com.example.gmcaching

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gmcaching.adapter.ItemListAdapter
import com.example.gmcaching.data.Item
import com.example.gmcaching.databinding.DatabaseFragmentBinding
import com.google.android.gms.location.*


class DatabaseFragment : Fragment() {
    companion object {
        fun newInstance() = DatabaseFragment()
    }
    private var _binding : DatabaseFragmentBinding?= null//??
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private val itemViewModel: ItemViewModel by viewModels {
        ItemViewModel.WordViewModelFactory((this.requireActivity().application as ItemApplication).repository)
    }

    private var newLat :Double =0.0
    private var newLng :Double =0.0
    private lateinit var newTitle: String
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var myDataset : LiveData<List<Item>>


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

        myDataset=itemViewModel.allItems
        val adapter = ItemListAdapter(this.requireContext(),myDataset)
        recyclerView = binding.recyclerview
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
            itemViewModel.insertItem(item)
                arguments?.clear()
                }

        }
    }



}