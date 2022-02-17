package com.example.gmcaching

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.*
import java.lang.Exception
import java.util.*

class Signup : AppCompatActivity() {

    private lateinit var username :EditText
    private lateinit var logemail: EditText
    private lateinit var logpasswort: EditText
    private lateinit var btnSignup: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var buttonGallery : ImageView
    private var localProfilePictureUri : Uri? = null
    private val newGalleryActivityRequestCode = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)


        mAuth = FirebaseAuth.getInstance()
        username = findViewById(R.id.sign_username)
        logemail = findViewById(R.id.sign_Email)
        logpasswort = findViewById(R.id.sign_passwort)
        btnSignup = findViewById(R.id.button_signup2)
        buttonGallery = findViewById(R.id.profileImageView)

        buttonGallery.setOnClickListener {
            checkCameraPermissions()
            val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
            galleryIntent.setType("image/")
            startActivityForResult(Intent.createChooser(galleryIntent,"Title"),newGalleryActivityRequestCode)
        }

        btnSignup.setOnClickListener{
            if (username.text.isNotEmpty()&&logemail.text.isNotEmpty()&&logpasswort.text.isNotEmpty()){
            val email =logemail.text.toString()
            val password =logpasswort.text.toString()

            signUp(email,password)
        }}
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
              if (requestCode==newGalleryActivityRequestCode&&resultCode== RESULT_OK)
        {
            val galleryURI= data?.data
            if (galleryURI!=null)
                localProfilePictureUri=galleryURI
            buttonGallery.setImageURI(galleryURI)
        }


    }


    private fun signUp(email:String, password: String) {
        //Logik für user Erstellung
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success
                        updateProfile()
                val intent = Intent(this@Signup, MainActivity::class.java )
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@Signup, "Authentifizierungsfehler", Toast.LENGTH_SHORT)
                }
            }
    }
    private fun updateProfile(){
        mAuth.currentUser?.let {  user->
        val username = username.text.toString()
        if (localProfilePictureUri==null) {
            localProfilePictureUri = Uri.parse("android.resource://$packageName/${R.drawable.defaultavatar}")
        }
       val profileupdates = UserProfileChangeRequest.Builder()
           .setDisplayName(username)
           .setPhotoUri(localProfilePictureUri)
           .build()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                user.updateProfile(profileupdates)
                awaitCancellation()
            }catch (e:Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(this@Signup, e.message,Toast.LENGTH_LONG)
                }
            }
        }
        }

    }

    private fun checkCameraPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 100)


        }
    }
}