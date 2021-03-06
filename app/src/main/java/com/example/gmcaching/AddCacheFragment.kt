package com.example.gmcaching

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.MediaStore
import android.provider.Settings
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gmcaching.data.Cache
import com.example.gmcaching.databinding.AddCacheFragmentBinding
import com.example.gmcaching.model.SharedViewModel
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.storage.FirebaseStorage
import java.util.*


class AddCacheFragment : Fragment() {
    companion object {
        fun newInstance() = AddCacheFragment()
    }

    private var _binding: AddCacheFragmentBinding? = null
    private val binding get() = _binding!!

    private var newLat :Double =0.0
    private var newLng :Double =0.0
    private var cacheImageUri : Uri? =null
    private val newCacheActivityRequestCode = 10001
    private val newCameraActivityRequestCode = 10002
    private val newGalleryActivityRequestCode=10003
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private val sharedViewModel: SharedViewModel by viewModels {
        SharedViewModel.SharedViewModelFactory()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddCacheFragmentBinding.inflate(inflater, container, false)
        val view = binding.root


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.requireContext())

        checkPermissions()
        // updates location data
        updateLocation()
        // Handle display rotation
        handleOrientationChange(savedInstanceState)


//Open Camera Button listener
        binding.buttonCamera.setOnClickListener {
        checkCameraPermissions()
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent,newCameraActivityRequestCode)

        }

//Open Gallery Button listener
        binding.buttonGallery.setOnClickListener {
            checkCameraPermissions()
            val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
            galleryIntent.type = "image/"
            startActivityForResult(Intent.createChooser(galleryIntent,"Title"),newGalleryActivityRequestCode)
        }

        binding.buttonSave.setOnClickListener {
            // nicht zu db hinzuf??gen
            if (newLat==0.0 && newLng==0.0) {
                Toast.makeText(
                    this.requireContext(),
                    R.string.locationError,
                    Toast.LENGTH_SHORT
                ).show()

                val action = AddCacheFragmentDirections.actionAddCacheFragmentToCacheListFragment()
                findNavController().navigate(action)

            }
            // nicht zu db hinzuf??gen
            else if (textfieldsAreEmpty()){

                Toast.makeText(
                    this.requireContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG
                ).show()

                val action = AddCacheFragmentDirections.actionAddCacheFragmentToCacheListFragment()
                findNavController().navigate(action)

            }else {
                //zu db hinzuf??gen
                 //Mit custom image
                if (cacheImageUri!=null) {
                    val imageid = uploadImage()
                    val cache = Cache(
                        creatorid = "",
                        cacheName = binding.editName.text.toString(),
                        lat = newLat,
                        lng = newLng,
                        image = imageid
                    )
                    sharedViewModel.insertItem(cache)

                    Toast.makeText(
                        context,
                        "Erfolgreich hinzugef??gt",
                        Toast.LENGTH_LONG
                    ).show()


                    val action =
                        AddCacheFragmentDirections.actionAddCacheFragmentToCacheListFragment()
                    findNavController().navigate(action)

                }
                //Mit default Image
                else{

                    val cache = Cache(
                        creatorid = "",
                        cacheName = binding.editName.text.toString(),
                        lat = newLat,
                        lng = newLng,
                        image = "null"
                    )
                    sharedViewModel.insertItem(cache)

                    Toast.makeText(
                        context,
                        "Erfolgreich hinzugef??gt",
                        Toast.LENGTH_LONG
                    ).show()


                    val action =
                        AddCacheFragmentDirections.actionAddCacheFragmentToCacheListFragment()
                    findNavController().navigate(action)
                }
            }
        }
    }

    private fun handleOrientationChange(savedInstanceState: Bundle?) {
        if (savedInstanceState!=null) {
             newLat=savedInstanceState.getDouble("lat")
             newLng=savedInstanceState.getDouble("lat")
            cacheImageUri=Uri.parse(savedInstanceState.getString("imageUri"))
            if (cacheImageUri!=null) {
                binding.userImageView.setImageURI(cacheImageUri)
                binding.infoStandardbild.text = getString(R.string.your_Image)

            }


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==newCameraActivityRequestCode)
        {
             val cameraImageBitmap= data?.extras?.get("data") as Bitmap?
            if (cameraImageBitmap!=null)
                cacheImageUri= Uri.parse(MediaStore.Images.Media.insertImage(context?.contentResolver,cameraImageBitmap,UUID.randomUUID().toString(),"Image Taken in GMCaching"))
            binding.userImageView.setImageURI(cacheImageUri)
            binding.infoStandardbild.text=getString(R.string.your_Image)

        }

        if (requestCode==newGalleryActivityRequestCode)
        {
            val galleryURI= data?.data
            if (galleryURI!=null)
                cacheImageUri=galleryURI
                binding.userImageView.setImageURI(galleryURI)
            binding.infoStandardbild.text=getString(R.string.your_Image)
        }


        }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==newCacheActivityRequestCode&& grantResults.isNotEmpty()&&(grantResults[0]+grantResults[1] == PackageManager.PERMISSION_GRANTED))
        {
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("imageUri",cacheImageUri.toString() )
        outState.putDouble("lat",newLat)
        outState.putDouble("lng",newLng)
    }


    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this.requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED||
            ContextCompat.checkSelfPermission(this.requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(this.requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), newCacheActivityRequestCode)
            ActivityCompat.requestPermissions(this.requireActivity(),
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), newCacheActivityRequestCode)
            Toast.makeText(
                this.requireContext(),
                R.string.needGpsHint,
                Toast.LENGTH_LONG
            ).show()

        }
    }
    private fun checkCameraPermissions() {
        if (ContextCompat.checkSelfPermission(this.requireActivity(), Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(this.requireActivity(), arrayOf(Manifest.permission.CAMERA), 100)


        }
    }


    @SuppressLint("MissingPermission")
    private fun updateLocation(){
        val locationmanager =  this.requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val locationRequest: LocationRequest =
            LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10000)
                .setFastestInterval(1000)
                .setNumUpdates(1)

        if (locationmanager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationmanager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER))

        {
            fusedLocationClient.flushLocations()
            fusedLocationClient.lastLocation.addOnCompleteListener {


                locationCallback = object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        super.onLocationResult(locationResult)

                        newLat = locationResult.lastLocation.latitude
                        newLng = locationResult.lastLocation.longitude


                    }
                }
                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.myLooper()!!
                )

            }


        }
        else{
            Toast.makeText(
                this.requireContext(),
                R.string.needGpsHint,
                Toast.LENGTH_SHORT
            ).show()
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))

        }



    }

    private fun textfieldsAreEmpty(): Boolean {
        return TextUtils.isEmpty(binding.editName.text)

    }



    private fun uploadImage():String{

        val storage = FirebaseStorage.getInstance("gs://real-gm-caching-97159.appspot.com/")
        val storageReference = storage.reference
        var imageKey :String= UUID.randomUUID().toString()



// Create a reference to 'images/imagekey'
        val imageRef = storageReference.child("images/"+imageKey)

        cacheImageUri?.let { imageRef.putFile(it).addOnSuccessListener {
            Toast.makeText(
                this.requireContext(),
                "Bild Erfolgreich hochgeladen",
                Toast.LENGTH_SHORT
            ).show()
        }.addOnFailureListener{
            Toast.makeText(
                this.requireContext(),
                "Fehler, Bild nicht hochgeladen",
                Toast.LENGTH_SHORT
            ).show()
            imageKey="null"
        } }
        return imageKey
    }



}