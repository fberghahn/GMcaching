package com.example.gmcaching

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class Login : AppCompatActivity() {

    private lateinit var logemail:  EditText
    private lateinit var logpasswort:  EditText
    private lateinit var btnlogin:  Button
    private lateinit var btnSignup: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        logemail = findViewById(R.id.log_Email)
        logpasswort = findViewById(R.id.log_passwort)
        btnlogin = findViewById(R.id.button_login)
        btnSignup = findViewById(R.id.button_signup)

        btnSignup.setOnClickListener{
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
        }
    }
}