package com.example.gmcaching

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
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
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import kotlin.collections.ArrayList


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
    private lateinit var myImageList : ArrayList<Bitmap>


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
//        myImageList= ArrayList<Bitmap>()



        val adapter = ItemListAdapter(this.requireContext(),myDataset)
        recyclerView = binding.recyclerview
        recyclerView.adapter=adapter
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        loadDataFromServer()



        binding.fab.setOnClickListener() {
            val action = DatabaseFragmentDirections.actionDatabaseFragmentToAddCacheFragment()
            findNavController().navigate(action)
        }

    }






    private fun loadDataFromServer() {
        val databaseRef = FirebaseDatabase.getInstance("https://real-gm-caching-97159-default-rtdb.europe-west1.firebasedatabase.app").reference
        databaseRef.child("Cache").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                myDataset.clear()
//                myImageList.clear()
                for (postSnapshot in snapshot.children)
                {
                    val currentCache=postSnapshot.getValue(Cache::class.java)
                    myDataset.add(currentCache!!)
//                    myImageList.add(getImage(currentCache.image))
                }
                Log.d("DatabaseRead", "Value is: $myDataset")
//                Toast.makeText(
//                    requireContext(),
//                    "Data loaded sucessfully",
//                    Toast.LENGTH_LONG
//                ).show()
               recyclerView.adapter?.notifyDataSetChanged()
            }

//            private fun getImage(image: String?): Bitmap {
//                val storage = FirebaseStorage.getInstance()
//
//                val pathReference = storage.getReferenceFromUrl(
//                    "https://firebasestorage.googleapis.com/v0/b/real-gm-caching-97159.appspot.com/o/images%2F")
//                var useableImage: Bitmap =ContextCompat.getDrawable(requireContext(), R.drawable.image1)!!.toBitmap()
//                pathReference.child(image!!).getBytes(1024*1024).addOnSuccessListener {
//                    useableImage=BitmapFactory.decodeByteArray(it,0,it.size)
////            useableImage=ContextCompat.getDrawable(context, R.drawable.image2)!!.toBitmap()
//
//                }.addOnFailureListener {
//                    useableImage=ContextCompat.getDrawable(requireContext(), R.drawable.image2)!!.toBitmap()
//                }
//                return useableImage
//            }


            override fun onCancelled(error: DatabaseError) {
                Log.w("DatabaseRead", "Failed to read value.", error.toException())
//                Toast.makeText(
//                    context,
//                    "Data Could not load, try enabling wifi",
//                    Toast.LENGTH_LONG
//                ).show()

            }

        })
    }




}