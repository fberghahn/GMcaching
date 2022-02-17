package com.example.gmcaching

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gmcaching.data.Cache
import com.example.gmcaching.model.SharedViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var cacheLocation: LatLng
    private lateinit var title: String
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var cacheid: String
    private lateinit var creatorid: String
    private var imageString: String = "null"
    private lateinit var foundButton: Button
    private val sharedViewModel: SharedViewModel by viewModels {
        SharedViewModel.SharedViewModelFactory()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this.requireActivity())
        mapFragment.getMapAsync(this)
        foundButton = view.findViewById(R.id.found_cache_button)
    }


    override fun onMapReady(p0: GoogleMap) {
        arguments?.let {
            cacheLocation =
                LatLng(it.getString("lat")!!.toDouble(), it.getString("lng")!!.toDouble())
            title = it.getString("title").toString()
            cacheid = it.getString("cacheid").toString()
            creatorid = it.getString("creatorid").toString()
            imageString = it.getString("imageString")!!
        }
        arguments?.clear()
        mMap = p0
        mMap.uiSettings.isZoomControlsEnabled = true

        setupMap()

        //Wenn Found-Button gedrückt wird, wird der Cache geupdated
        foundButton.setOnClickListener {
            val currentCache = Cache(
                cacheid,
                creatorid,
                title,
                cacheLocation.latitude,
                cacheLocation.longitude,
                imageString
            )
            currentCache.found = true
            currentCache.cacheName = currentCache.cacheName + " (Gefunden)"
            sharedViewModel.updateCache(currentCache)

            val action = MapsFragmentDirections.actionMapsFragmentToDatabaseFragment()
            findNavController().navigate(action)
        }


    }


    private fun setMarker() {
        val circleOptions = circlePrep()


        mMap.addMarker(MarkerOptions().position(cacheLocation).title(title))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cacheLocation))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cacheLocation, 14f))
        mMap.addCircle(circleOptions)

    }

    @SuppressLint("MissingPermission")
    private fun setupMap() {

        if (ActivityCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mMap.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(this.requireActivity()) { location ->
            if (location != null) {
                lastLocation = location


            }
        }
        setMarker()
    }

    private fun circlePrep(): CircleOptions {
        val circleOptions = CircleOptions()
        circleOptions.center(cacheLocation)
        circleOptions.radius(40.0)
        circleOptions.visible(true)
        circleOptions.fillColor(0x8080ff80.toInt())
        circleOptions.strokeColor(0x8080ff80.toInt())
        return circleOptions
    }

}