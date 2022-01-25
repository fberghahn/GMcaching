package com.example.gmcaching

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.gmcaching.adapter.ItemAdapter
import com.example.gmcaching.data.Datasource
import android.content.Intent




class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Initialize data.
        val myDataset = Datasource().loadcaches()

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = ItemAdapter(this, myDataset)


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

    }


