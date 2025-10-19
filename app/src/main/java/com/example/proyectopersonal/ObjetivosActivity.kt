package com.example.proyectopersonal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ObjetivosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_objetivos)
        
        val btnPerderGrasa = findViewById<Button>(R.id.btnPerderGrasa)
        val btnGanarMusculo = findViewById<Button>(R.id.btnGanarMusculo)
        val btnMantenerPeso = findViewById<Button>(R.id.btnMantenerPeso)
        
        btnPerderGrasa.setOnClickListener {
            iniciarApp("perder_grasa")
        }
        
        btnGanarMusculo.setOnClickListener {
            iniciarApp("ganar_musculo")
        }
        
        btnMantenerPeso.setOnClickListener {
            iniciarApp("mantener_peso")
        }
    }
    
    private fun iniciarApp(objetivo: String) {
        val intent = Intent(this, CaloriasActivity::class.java)
        intent.putExtra("objetivo", objetivo)
        startActivity(intent)
    }
}