package com.example.gmcaching

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.gmcaching.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment(), GoogleMap.OnMarkerClickListener, OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var cacheLocation : LatLng
    private lateinit var title : String
    private lateinit var mapFragment : SupportMapFragment



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

        mapFragment  = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.requireActivity())
        mapFragment.getMapAsync(this)
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        TODO("Not yet implemented")
    }

    override fun onMapReady(p0: GoogleMap) {
        arguments?.let {
            cacheLocation = LatLng(it.getString("lat")!!.toDouble(),it.getString("lng")!!.toDouble())
            title=it.getString("title").toString()
        }
        title= title.toString()
        mMap = p0
        mMap.uiSettings.isZoomControlsEnabled = true
//        mMap.setOnMarkerClickListener(this)

        setupMap()




    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private fun setMarker(){
        val circleOptions = circlePrep()


        mMap.addMarker(MarkerOptions().position(cacheLocation).title(title))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cacheLocation))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cacheLocation, 13f))
        mMap.addCircle(circleOptions)

    }

    @SuppressLint("MissingPermission")
    private fun setupMap(){

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

    private fun circlePrep(): CircleOptions{
        val circleOptions :CircleOptions = CircleOptions()
        circleOptions.center(cacheLocation)
        circleOptions.radius(40.0)
        circleOptions.visible(true)
        circleOptions.fillColor(0x8080ff80.toInt())
        circleOptions.strokeColor(0x8080ff80.toInt())
        return circleOptions
    }
    public fun getlastLocation(): Location {
        return lastLocation
    }
}