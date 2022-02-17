package com.example.gmcaching

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth

class LoginStateActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_state)

        mAuth = FirebaseAuth.getInstance( )
        if (mAuth.currentUser!=null){
            val intent = Intent(this@LoginStateActivity, MainActivity::class.java )
            startActivity(intent)
            finish()
        }
        else
        {
            val intent = Intent(this@LoginStateActivity, Login::class.java )
            startActivity(intent)
            finish()
        }
    }

}