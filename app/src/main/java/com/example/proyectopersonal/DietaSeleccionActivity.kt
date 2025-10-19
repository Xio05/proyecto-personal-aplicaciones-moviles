package com.example.proyectopersonal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class DietaSeleccionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dieta_seleccion)
        
        val btnRecomendada = findViewById<Button>(R.id.btnRecomendada)
        val btnAltaProteina = findViewById<Button>(R.id.btnAltaProteina)
        val btnBajaCarbohidratos = findViewById<Button>(R.id.btnBajaCarbohidratos)
        val btnKeto = findViewById<Button>(R.id.btnKeto)
        val btnBajaGrasas = findViewById<Button>(R.id.btnBajaGrasas)
        
        btnRecomendada.setOnClickListener {
            iniciarApp("recomendada")
        }
        
        btnAltaProteina.setOnClickListener {
            iniciarApp("alta_proteina")
        }
        
        btnBajaCarbohidratos.setOnClickListener {
            iniciarApp("baja_carbohidratos")
        }
        
        btnKeto.setOnClickListener {
            iniciarApp("keto")
        }
        
        btnBajaGrasas.setOnClickListener {
            iniciarApp("baja_grasas")
        }
    }
    
    private fun iniciarApp(tipoDieta: String) {
        val intent = Intent(this, PersonalizarObjetivoActivity::class.java)
        intent.putExtra("tipo_dieta", tipoDieta)
        startActivity(intent)
    }
}