package com.example.proyectopersonal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SeleccionAlimentosActivity : AppCompatActivity() {
    
    private val proteinasSeleccionadas = mutableListOf<String>()
    private val carbohidratosSeleccionados = mutableListOf<String>()
    private val grasasSeleccionadas = mutableListOf<String>()
    private val frutasSeleccionadas = mutableListOf<String>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccion_alimentos)
        
        configurarCheckboxes()
        
        val btnCrearPlan = findViewById<Button>(R.id.btnCrearPlan)
        btnCrearPlan.setOnClickListener {
            if (validarSelecciones()) {
                val intent = Intent(this, DietaMainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
    
    private fun configurarCheckboxes() {
        // Proteínas
        val proteinas = listOf("pollo", "carne", "pescado", "atun", "langosta", "huevo", "pavo", "chancho", "jamon")
        proteinas.forEach { proteina ->
            val checkboxId = resources.getIdentifier("cb_$proteina", "id", packageName)
            findViewById<CheckBox>(checkboxId)?.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) proteinasSeleccionadas.add(proteina)
                else proteinasSeleccionadas.remove(proteina)
            }
        }
        
        // Carbohidratos
        val carbohidratos = listOf("arroz", "papa", "camote", "yuca", "lentejas", "frijoles", "garbanzo", "arveja", "quinua")
        carbohidratos.forEach { carbo ->
            val checkboxId = resources.getIdentifier("cb_$carbo", "id", packageName)
            findViewById<CheckBox>(checkboxId)?.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) carbohidratosSeleccionados.add(carbo)
                else carbohidratosSeleccionados.remove(carbo)
            }
        }
        
        // Grasas
        val grasas = listOf("palta", "mani", "mantequilla_mani", "almendras", "pecanas", "nueces")
        grasas.forEach { grasa ->
            val checkboxId = resources.getIdentifier("cb_$grasa", "id", packageName)
            findViewById<CheckBox>(checkboxId)?.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) grasasSeleccionadas.add(grasa)
                else grasasSeleccionadas.remove(grasa)
            }
        }
        
        // Frutas
        val frutas = listOf("platano", "fresas", "manzana", "arandano", "pina", "papaya")
        frutas.forEach { fruta ->
            val checkboxId = resources.getIdentifier("cb_$fruta", "id", packageName)
            findViewById<CheckBox>(checkboxId)?.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) frutasSeleccionadas.add(fruta)
                else frutasSeleccionadas.remove(fruta)
            }
        }
    }
    
    private fun validarSelecciones(): Boolean {
        if (proteinasSeleccionadas.size < 2) {
            Toast.makeText(this, "Selecciona al menos 2 proteínas", Toast.LENGTH_SHORT).show()
            return false
        }
        if (carbohidratosSeleccionados.size < 3) {
            Toast.makeText(this, "Selecciona al menos 3 carbohidratos", Toast.LENGTH_SHORT).show()
            return false
        }
        if (grasasSeleccionadas.size < 2) {
            Toast.makeText(this, "Selecciona al menos 2 grasas", Toast.LENGTH_SHORT).show()
            return false
        }
        if (frutasSeleccionadas.size < 2) {
            Toast.makeText(this, "Selecciona al menos 2 frutas", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}