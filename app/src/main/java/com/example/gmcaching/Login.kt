package com.example.gmcaching

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {

    private lateinit var logemail: EditText
    private lateinit var logpasswort: EditText
    private lateinit var btnlogin: Button
    private lateinit var btnSignup: Button
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        mAuth = FirebaseAuth.getInstance( )
        logemail = findViewById(R.id.log_Email)
        logpasswort = findViewById(R.id.log_passwort)
        btnlogin = findViewById(R.id.button_login)
        btnSignup = findViewById(R.id.button_signup)

        btnSignup.setOnClickListener {
            val intent = Intent(this@Login, Signup::class.java)
            startActivity(intent)
        }
        btnlogin.setOnClickListener {
            val email = logemail.text.toString()
            val password = logpasswort.text.toString()

            login(email, password)
        }
    }

    private fun login(email:String, password:String){
        //Logik für User einloggen
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this@Login, MainActivity::class.java )
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@Login, "Nutzer nicht gefunden", Toast.LENGTH_SHORT).show()
                }
            }

    }
}