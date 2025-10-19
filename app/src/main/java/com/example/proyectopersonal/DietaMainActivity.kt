package com.example.proyectopersonal

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DietaMainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dieta_main)
        
        val tvBienvenida = findViewById<TextView>(R.id.tvBienvenida)
        tvBienvenida.text = "¡Bienvenido a tu plan de dieta personalizado!"
    }
}