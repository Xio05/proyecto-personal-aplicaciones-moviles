package com.example.proyectopersonal.models

// Tipos de datos y clases
data class Usuario(
    val id: Int,
    var nombre: String,
    var email: String,
    var pesoActual: Double,
    var pesoObjetivo: Double,
    var altura: Int, // en cm
    var edad: Int,
    var genero: String,
    var nivelActividad: String
) {
    // Métodos de la clase
    fun calcularIMC(): Double {
        val alturaEnMetros = altura / 100.0
        return pesoActual / (alturaEnMetros * alturaEnMetros)
    }
    
    fun calcularCaloriasBasales(): Int {
        // Fórmula Harris-Benedict
        return if (genero == "Masculino") {
            (88.362 + (13.397 * pesoActual) + (4.799 * altura) - (5.677 * edad)).toInt()
        } else {
            (447.593 + (9.247 * pesoActual) + (3.098 * altura) - (4.330 * edad)).toInt()
        }
    }
}

data class Alimento(
    val id: Int,
    var nombre: String,
    var calorias: Int, // por 100g
    var proteinas: Double,
    var carbohidratos: Double,
    var grasas: Double,
    var fibra: Double,
    var categoria: String
)

data class ComidaConsumida(
    val id: Int,
    val alimentoId: Int,
    var cantidad: Double, // en gramos
    val fecha: String,
    val tipoComida: String,
    val usuarioId: Int
) {
    fun calcularCaloriasConsumidas(alimento: Alimento): Int {
        return ((alimento.calorias * cantidad) / 100).toInt()
    }
}

data class RegistroDiario(
    val id: Int,
    val fecha: String,
    var caloriasConsumidas: Int,
    var caloriasObjetivo: Int,
    var pesoRegistrado: Double?,
    val usuarioId: Int
)

// Enums para tipos de datos
enum class TipoComida(val valor: String) {
    DESAYUNO("Desayuno"),
    ALMUERZO("Almuerzo"),
    CENA("Cena"),
    SNACK("Snack")
}

enum class CategoriaAlimento(val valor: String) {
    FRUTAS("Frutas"),
    VERDURAS("Verduras"),
    PROTEINAS("Proteínas"),
    CARBOHIDRATOS("Carbohidratos"),
    LACTEOS("Lácteos"),
    GRASAS("Grasas")
}