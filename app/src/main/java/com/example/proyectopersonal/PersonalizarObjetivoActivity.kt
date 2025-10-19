package com.example.proyectopersonal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PersonalizarObjetivoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personalizar_objetivo)
        
        val etPesoActual = findViewById<EditText>(R.id.etPesoActual)
        val etPesoObjetivo = findViewById<EditText>(R.id.etPesoObjetivo)
        val btnCrearPlan = findViewById<Button>(R.id.btnCrearPlan)
        
        btnCrearPlan.setOnClickListener {
            val pesoActual = etPesoActual.text.toString()
            val pesoObjetivo = etPesoObjetivo.text.toString()
            
            if (pesoActual.isNotEmpty() && pesoObjetivo.isNotEmpty()) {
                val intent = Intent(this, SeleccionAlimentosActivity::class.java)
                intent.putExtra("peso_actual", pesoActual)
                intent.putExtra("peso_objetivo", pesoObjetivo)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Complete ambos campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}