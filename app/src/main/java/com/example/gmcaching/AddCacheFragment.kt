package com.example.gmcaching

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.gmcaching.databinding.AddCacheFragmentBinding
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnCompleteListener


class AddCacheFragment : Fragment() {
    companion object {
        fun newInstance() = AddCacheFragment()
    }

    private lateinit var editWordView: EditText
    private var _binding: AddCacheFragmentBinding? = null
    private val binding get() = _binding!!

    private var newLat :Double =0.0
    private var newLng :Double =0.0
    private lateinit var title : String
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddCacheFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.requireContext())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermissions()
        updateLocation()

        binding.buttonSave.setOnClickListener {
            if (textfieldsAreFilled()) {
                val action = AddCacheFragmentDirections.actionAddCacheFragmentToDatabaseFragment(
                    null.toString(), "0.0","0.0",  false
                )
                findNavController().navigate(action)
            } else {
                val action = AddCacheFragmentDirections.actionAddCacheFragmentToDatabaseFragment(
                    binding.editName.text.toString(), newLat.toString(), newLng.toString() ,true)
                findNavController().navigate(action)


            }
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
            updateLocation()
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

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this.requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED&&
            ContextCompat.checkSelfPermission(this.requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED) {



        } else {
            ActivityCompat.requestPermissions(this.requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 10001)
            ActivityCompat.requestPermissions(this.requireActivity(),
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 10001)
            Toast.makeText(
                this.requireContext(),
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }


    @SuppressLint("MissingPermission")
    private fun updateLocation(){
        val locationmanager =  this.requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationmanager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationmanager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER))
        {
            fusedLocationClient.flushLocations()
            fusedLocationClient.lastLocation.addOnCompleteListener(OnCompleteListener { task ->

                    val locationRequest : LocationRequest = LocationRequest().
                    setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                        .setInterval(10000)
                        .setFastestInterval(1000)
                        .setNumUpdates(1)
                    locationCallback = object : LocationCallback() {
                        override fun onLocationResult(locationResult: LocationResult) {
                            super.onLocationResult(locationResult)

                            val location : Location = locationResult.lastLocation
                            newLat=location.latitude
                            newLng=location.longitude


                        }
                    }
                    fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper()!!)

            })
        }
        else{
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
//        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
//            if (location != null) {
//                newLat=location.latitude
//                newLng=location.longitude
//
//            }
//
//        }

    }

    private fun textfieldsAreFilled(): Boolean {
        return TextUtils.isEmpty(binding.editName.text)

    }


}