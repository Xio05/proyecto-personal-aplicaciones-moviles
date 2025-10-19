package com.example.proyectopersonal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class CaloriasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calorias)
        
        val btnContinuar = findViewById<Button>(R.id.btnContinuar)
        
        btnContinuar.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }
    }
}