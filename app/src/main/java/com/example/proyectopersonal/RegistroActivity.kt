package com.example.proyectopersonal

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        
        val spinnerSexo = findViewById<Spinner>(R.id.spinnerSexo)
        val etEdad = findViewById<EditText>(R.id.etEdad)
        val etAltura = findViewById<EditText>(R.id.etAltura)
        val etPeso = findViewById<EditText>(R.id.etPeso)
        val btnContinuar = findViewById<Button>(R.id.btnContinuar)
        
        // Configurar spinner de sexo
        val sexos = arrayOf("Hombre", "Mujer")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sexos)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSexo.adapter = adapter
        
        btnContinuar.setOnClickListener {
            val sexo = spinnerSexo.selectedItem.toString()
            val edad = etEdad.text.toString()
            val altura = etAltura.text.toString()
            val peso = etPeso.text.toString()
            
            if (edad.isNotEmpty() && altura.isNotEmpty() && peso.isNotEmpty()) {
                val intent = Intent(this, DietaSeleccionActivity::class.java)
                intent.putExtra("sexo", sexo)
                intent.putExtra("edad", edad)
                intent.putExtra("altura", altura)
                intent.putExtra("peso", peso)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}