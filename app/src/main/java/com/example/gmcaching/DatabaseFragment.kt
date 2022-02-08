package com.example.gmcaching

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gmcaching.adapter.ItemAdapter
import com.example.gmcaching.adapter.ItemListAdapter
import com.example.gmcaching.data.Item
import com.example.gmcaching.databinding.DatabaseFragmentBinding
import com.example.gmcaching.model.Cache
import com.google.android.gms.maps.model.LatLng


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

    private  var newLat: Double =0.0
    private  var newLng: Double =0.0
    private lateinit var newTitle: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DatabaseFragmentBinding.inflate(inflater, container, false)//??
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recyclerView = binding.recyclerview
        val adapter = ItemListAdapter()
        recyclerView.adapter=adapter
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())


        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
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

    private fun handleData() {
        if (arguments?.let { it.getBoolean("text_is_ready")} == true) {
            arguments?.let {
                newLat = it.getString("lat")!!.toDouble()
                newLng=it.getString("lng")!!.toDouble()
                newTitle=it.getString("name").toString()
            }
            val item = Item(cacheName = newTitle, lat = newLat, lng = newLng, suchcounter = 3)
            itemViewModel.insert(item)

        } else {
            Toast.makeText(
                this.requireContext(),
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }


}