package com.example.proyectopersonal

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MenuDietaActivity : AppCompatActivity() {
    
    private var aguaTotal = 0
    
    private val alimentos = mapOf(
        // Proteínas
        "pollo" to 165, "carne" to 250, "pescado" to 120, "atun" to 144, 
        "langosta" to 89, "huevo" to 155, "pavo" to 135, "chancho" to 242, "jamon" to 145,
        // Carbohidratos
        "arroz" to 130, "papa" to 77, "camote" to 86, "yuca" to 160, 
        "lentejas" to 116, "frijoles" to 127, "garbanzo" to 164, "arveja" to 81, "quinua" to 120,
        // Grasas
        "palta" to 160, "mani" to 567, "pecanas" to 691, "nueces" to 654,
        // Frutas
        "platano" to 89, "manzana" to 52, "arandano" to 57, "papaya" to 43
    )
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_dieta)
        
        configurarFecha()
        configurarEventos()
    }
    
    private fun configurarFecha() {
        val calendar = Calendar.getInstance()
        val formatoFecha = SimpleDateFormat("dd 'de' MMMM 'de' yyyy", Locale("es", "ES"))
        val formatoDia = SimpleDateFormat("EEEE", Locale("es", "ES"))
        
        findViewById<TextView>(R.id.tvFecha).text = formatoFecha.format(calendar.time)
        findViewById<TextView>(R.id.tvDiaSemana).text = formatoDia.format(calendar.time).capitalize()
    }
    
    private fun configurarEventos() {
        findViewById<Button>(R.id.btnAgregarAgua).setOnClickListener {
            val etAgua = findViewById<EditText>(R.id.etAgua)
            val cantidad = etAgua.text.toString().toIntOrNull() ?: 0
            if (cantidad > 0) {
                aguaTotal += cantidad
                findViewById<TextView>(R.id.tvAguaTotal).text = "Total: $aguaTotal ml"
                etAgua.text.clear()
            }
        }
        
        findViewById<Button>(R.id.btnRegistrarPeso).setOnClickListener {
            val etPeso = findViewById<EditText>(R.id.etPeso)
            val peso = etPeso.text.toString().toFloatOrNull()
            if (peso != null && peso > 0) {
                findViewById<TextView>(R.id.tvPesoActual).text = "Peso: $peso kg"
                etPeso.text.clear()
            }
        }
        
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) { calcularCalorias() }
        }
        
        findViewById<EditText>(R.id.etDesayuno).addTextChangedListener(textWatcher)
        findViewById<EditText>(R.id.etAlmuerzo).addTextChangedListener(textWatcher)
        findViewById<EditText>(R.id.etCena).addTextChangedListener(textWatcher)
        findViewById<EditText>(R.id.etBocadillo).addTextChangedListener(textWatcher)
    }
    
    private fun calcularCalorias() {
        val calDesayuno = calcularCaloriasComida(findViewById<EditText>(R.id.etDesayuno).text.toString())
        val calAlmuerzo = calcularCaloriasComida(findViewById<EditText>(R.id.etAlmuerzo).text.toString())
        val calCena = calcularCaloriasComida(findViewById<EditText>(R.id.etCena).text.toString())
        val calBocadillo = calcularCaloriasComida(findViewById<EditText>(R.id.etBocadillo).text.toString())
        
        findViewById<TextView>(R.id.tvCalDesayuno).text = "Calorías: $calDesayuno kcal"
        findViewById<TextView>(R.id.tvCalAlmuerzo).text = "Calorías: $calAlmuerzo kcal"
        findViewById<TextView>(R.id.tvCalCena).text = "Calorías: $calCena kcal"
        findViewById<TextView>(R.id.tvCalBocadillo).text = "Calorías: $calBocadillo kcal"
        
        val totalCalorias = calDesayuno + calAlmuerzo + calCena + calBocadillo
        findViewById<TextView>(R.id.tvCaloriasTotal).text = "$totalCalorias kcal"
    }
    
    private fun calcularCaloriasComida(texto: String): Int {
        return alimentos.filter { texto.lowercase().contains(it.key) }.values.sum()
    }
}