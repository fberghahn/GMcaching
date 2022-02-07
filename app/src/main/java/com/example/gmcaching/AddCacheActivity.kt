package com.example.gmcaching

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

class AddCacheActivity : AppCompatActivity() {

    private lateinit var editWordView: EditText
//    private lateinit var editKoordinatenView: EditText
//    private lateinit var editSuchcounterView: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_cache)
        editWordView = findViewById(R.id.edit_name)
//        editKoordinatenView = findViewById(R.id.edit_koordinaten)
//        editSuchcounterView = findViewById(R.id.edit_suchcounter)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editWordView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val name = editWordView.text.toString()
                //val koordinaten = editKoordinatenView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, name)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}
