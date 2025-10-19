package com.example.proyectopersonal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        
        val btnComenzar = findViewById<Button>(R.id.btnComenzar)
        val btnIniciarSesion = findViewById<Button>(R.id.btnIniciarSesion)
        
        btnComenzar.setOnClickListener {
            startActivity(Intent(this, ObjetivosActivity::class.java))
        }
        
        btnIniciarSesion.setOnClickListener {
            startActivity(Intent(this, DietaMainActivity::class.java))
        }
    }
}