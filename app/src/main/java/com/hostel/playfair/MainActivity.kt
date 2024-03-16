package com.hostel.playfair

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.options)
        val encryptButton = findViewById<Button>(R.id.encrypt)
        val decryptButton = findViewById<Button>(R.id.decrypt)
        val aboutButton = findViewById<Button>(R.id.about)
        encryptButton.setOnClickListener() {
            val intent = Intent(this, EncryptionActivity::class.java)
            startActivity(intent)
        }
        decryptButton.setOnClickListener() {
            val intent = Intent(this, DecryptionActivity::class.java)
            startActivity(intent)
        }

        aboutButton.setOnClickListener() {
            val intent = Intent(this, about::class.java)
            startActivity(intent)
        }
    }
}