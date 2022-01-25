package com.example.gmcaching

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.gmcaching.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)





      //val button = findViewById(R.id.button1) as Button

//    button.setOnClickListener(object : View.OnClickListener {
//        override fun onClick(view: View?) {
//            // Toast.makeText(this@MainActivity, "Viel Erfolg bei der Suche", Toast.LENGTH_SHORT).show() //Toast zum Klick auf den Cache
//            val intent = Intent(this@MainActivity, MapsActivity::class.java)
//            startActivity(intent)
//        }
    //}
    //)
}
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    }


