package com.example.gmcaching

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var logemail: EditText
    private lateinit var logpasswort: EditText
    private lateinit var btnlogin: Button
    private lateinit var btnSignup: Button
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        mAuth = FirebaseAuth.getInstance()
        logemail = findViewById(R.id.log_Email)
        logpasswort = findViewById(R.id.log_passwort)
        btnlogin = findViewById(R.id.button_login)
        btnSignup = findViewById(R.id.button_signup)
        mAuth.signOut()

        btnSignup.setOnClickListener {
            val intent = Intent(this@Login, Signup::class.java)
            startActivity(intent)
        }
        btnlogin.setOnClickListener {
            if (logemail.text.isNotEmpty() && logpasswort.text.isNotEmpty()) {
                val email = logemail.text.toString()
                val password = logpasswort.text.toString()

                login(email, password)
            }
        }
    }

    private fun login(email: String, password: String) {
        //Logik für User login

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this@Login, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@Login, getString(R.string.password_email_wrong), Toast.LENGTH_SHORT)
                        .show()
                }
            }

    }
}