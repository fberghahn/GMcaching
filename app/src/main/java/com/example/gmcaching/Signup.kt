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

class Signup : AppCompatActivity() {

    private lateinit var username :EditText
    private lateinit var logemail: EditText
    private lateinit var logpasswort: EditText
    private lateinit var btnSignup: Button
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)


        mAuth = FirebaseAuth.getInstance()
        username = findViewById(R.id.sign_username)
        logemail = findViewById(R.id.sign_Email)
        logpasswort = findViewById(R.id.sign_passwort)
        btnSignup = findViewById(R.id.button_signup2)

        btnSignup.setOnClickListener{
            val email =logemail.text.toString()
            val password =logpasswort.text.toString()

            signUp(email,password)
        }
    }
    private fun signUp(email:String, password: String) {
        //Logik für user Erstellung
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                val intent = Intent(this@Signup, MainActivity::class.java )
                    startActivity(intent)
                } else {
                    Toast.makeText(this@Signup, "Authentifizierungsfehler", Toast.LENGTH_SHORT)
                }
            }
    }
}