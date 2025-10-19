# 🥗 Aplicación de Dieta Saludable - Android

Una aplicación Android moderna y funcional que implementa **TODOS** los conceptos fundamentales del desarrollo Android usando Kotlin.

## 📱 Conceptos Implementados

### 1. **Tipos de datos, Variables (var, val)**
```kotlin
// Variables mutables e inmutables
var pesoActual: Double = 68.5
val altura: Int = 165

// Arreglos
private val caloriasSemanales = intArrayOf(1800, 1950, 1750, 2100, 1850, 2000, 1900)
private val diasSemana = arrayOf("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom")
```

### 2. **Funciones, Métodos, Clases y Objetos**
```kotlin
data class Usuario(
    val id: Int,
    var nombre: String,
    var pesoActual: Double,
    var altura: Int,
    var edad: Int
) {
    // Métodos de la clase
    fun calcularIMC(): Double {
        val alturaEnMetros = altura / 100.0
        return pesoActual / (alturaEnMetros * alturaEnMetros)
    }
    
    fun calcularCaloriasBasales(): Int {
        return if (genero == "Masculino") {
            (88.362 + (13.397 * pesoActual) + (4.799 * altura) - (5.677 * edad)).toInt()
        } else {
            (447.593 + (9.247 * pesoActual) + (3.098 * altura) - (4.330 * edad)).toInt()
        }
    }
}
```

### 3. **Secuencias Repetitivas (for, while, if/if-else)**
```kotlin
// Secuencia for con arreglos
for (i in caloriasSemanales.indices) {
    totalCalorias += caloriasSemanales[i]
    mensaje += "${diasSemana[i]}: ${caloriasSemanales[i]} kcal\n"
}

// Secuencia while con condicionales
var i = 0
while (i < alimentos.size) {
    val alimento = alimentos[i]
    if (categoria == "Todos" || alimento.categoria == categoria) {
        alimentosFiltrados.add(alimento)
    }
    i++
}

// Condicionales if/else para clasificar IMC
val clasificacionIMC = if (imc < 18.5) {
    "Bajo peso"
} else if (imc < 25.0) {
    "Peso normal"
} else if (imc < 30.0) {
    "Sobrepeso"
} else {
    "Obesidad"
}
```

### 4. **Eventos y Programación de Eventos**
```kotlin
// Eventos de click
bottomNavigation.setOnItemSelectedListener { item ->
    when (item.itemId) {
        R.id.nav_alimentos -> {
            mostrarFragmentAlimentos()
            true
        }
    }
}

// Eventos de menú
override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
        R.id.action_calcular_estadisticas -> {
            calcularEstadisticasAsync()
            true
        }
    }
}

// Eventos de sensores
override fun onSensorChanged(event: SensorEvent?) {
    event?.let {
        when (it.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> {
                // Procesar datos del acelerómetro
            }
        }
    }
}
```

### 5. **RecyclerView, Adaptadores, ViewHolder**
```kotlin
class AlimentoAdapter(
    private var alimentos: MutableList<Alimento>,
    private val onAlimentoClick: (Alimento) -> Unit
) : RecyclerView.Adapter<AlimentoAdapter.AlimentoViewHolder>() {
    
    // ViewHolder pattern
    class AlimentoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.tvAlimentoNombre)
        val caloriasTextView: TextView = itemView.findViewById(R.id.tvAlimentoCalorias)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlimentoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_alimento, parent, false)
        return AlimentoViewHolder(view)
    }
    
    // ArrayAdapter para Spinner
    val spinnerAdapter = ArrayAdapter(
        requireContext(),
        android.R.layout.simple_spinner_item,
        categorias
    )
}
```

### 6. **CardView - Diseño Avanzado**
```xml
<androidx.cardview.widget.CardView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:cardCornerRadius="12dp"
    app:cardElevation="6dp">
    
    <!-- Contenido del card -->
    
</androidx.cardview.widget.CardView>
```

### 7. **Fragments (Básicos y Dinámicos)**
```kotlin
// Fragment básico
class AlimentosFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_alimentos, container, false)
    }
}

// Fragment dinámico con argumentos
class DetalleAlimentoFragment : Fragment() {
    companion object {
        fun newInstance(alimentoId: Int): DetalleAlimentoFragment {
            val fragment = DetalleAlimentoFragment()
            val args = Bundle()
            args.putInt(ARG_ALIMENTO_ID, alimentoId)
            fragment.arguments = args
            return fragment
        }
    }
}

// Navegación entre fragments
val fragment = AlimentosFragment.newInstance()
supportFragmentManager.beginTransaction()
    .replace(R.id.fragment_container, fragment)
    .commit()
```

### 8. **Persistencia de Datos - SQLite**
```kotlin
class DietaDatabaseHelper : SQLiteOpenHelper {
    override fun onCreate(db: SQLiteDatabase) {
        val createAlimentosTable = """
            CREATE TABLE alimentos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                calorias INTEGER NOT NULL,
                proteinas REAL NOT NULL,
                carbohidratos REAL NOT NULL,
                grasas REAL NOT NULL
            )
        """.trimIndent()
        db.execSQL(createAlimentosTable)
    }
    
    // CRUD Operations
    fun insertarAlimento(alimento: Alimento): Long
    fun obtenerTodosLosAlimentos(): List<Alimento>
    fun actualizarAlimento(alimento: Alimento): Int
    fun eliminarAlimento(id: Int): Int
}
```

### 9. **Tareas Asíncronas - Thread, runOnUiThread, Coroutines**
```kotlin
// Thread tradicional
TareasAsincrona.ejecutarEnBackground(
    tarea = {
        Thread.sleep(2000) // Simular trabajo pesado
    },
    alCompletar = {
        Toast.makeText(this, "Completado", Toast.LENGTH_SHORT).show()
    }
)

// runOnUiThread pattern
activity.runOnUiThread {
    actualizarProgreso(progreso)
}

// Coroutines (recomendado)
TareasAsincrona.ejecutarConCorrutinas(
    scope = lifecycleScope,
    tareaBackground = {
        calcularEstadisticasDieta(calorias)
    },
    alExito = { resultado ->
        Toast.makeText(this, resultado, Toast.LENGTH_LONG).show()
    }
)
```

### 10. **Sensores (Acelerómetro, Luz, Proximidad)**
```kotlin
class DietaMainActivity : SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var lightSensor: Sensor? = null
    private var proximitySensor: Sensor? = null
    
    override fun onSensorChanged(event: SensorEvent?) {
        when (event?.sensor?.type) {
            Sensor.TYPE_ACCELEROMETER -> {
                val movimiento = kotlin.math.sqrt((x*x + y*y + z*z).toDouble())
                if (movimiento > 15) {
                    // Usuario activo - quemar calorías
                }
            }
            Sensor.TYPE_LIGHT -> {
                if (luz < 10) {
                    // Activar modo nocturno
                }
            }
            Sensor.TYPE_PROXIMITY -> {
                // Detectar proximidad
            }
        }
    }
}
```

### 11. **Networking - Volley (JSON)**
```kotlin
// Uso de Volley para obtener datos JSON
private fun procesarDatosConVolley() {
    val requestQueue = Volley.newRequestQueue(this)
    val url = "https://jsonplaceholder.typicode.com/posts"
    
    val jsonArrayRequest = JsonArrayRequest(
        Request.Method.GET, url, null,
        { response: JSONArray ->
            // Procesar respuesta JSON
            val totalPosts = response.length()
            
            // Procesar objetos JSON
            for (i in 0 until response.length()) {
                val post = response.getJSONObject(i)
                val titulo = post.getString("title")
                val contenido = post.getString("body")
            }
        },
        { error ->
            Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
        }
    )
    
    requestQueue.add(jsonArrayRequest)
}
```

## 🏗️ **Arquitectura MVC**

- **Model**: `DietaModels.kt`, `DietaDatabaseHelper.kt`
- **View**: Activities, Fragments, Layouts XML
- **Controller**: `DietaMainActivity.kt`, Adapters

## 🚀 **Funcionalidades de la App**

1. **Gestión de Alimentos**: Base de datos SQLite con alimentos y sus valores nutricionales
2. **Cálculo de IMC**: Métodos en clase Usuario para calcular IMC y calorías basales
3. **Sensores**: Monitoreo de acelerómetro, luz y proximidad
4. **Fragments Dinámicos**: Navegación entre lista de alimentos y detalles
5. **Tareas Asíncronas**: Cálculos en background con diferentes enfoques
6. **Networking**: Integración con APIs usando Volley
7. **Material Design**: UI moderna con CardView, BottomNavigation, etc.

## 📋 **Estructura del Proyecto**

```
app/src/main/java/com/example/proyectopersonal/
├── models/
│   └── DietaModels.kt          # Clases de datos, enums
├── database/
│   └── DietaDatabaseHelper.kt  # SQLite, CRUD operations
├── adapters/
│   └── AlimentoAdapter.kt      # RecyclerView.Adapter, ViewHolder
├── fragments/
│   ├── AlimentosFragment.kt    # Fragment básico
│   └── DetalleAlimentoFragment.kt # Fragment dinámico
├── utils/
│   └── TareasAsincrona.kt      # Thread, Coroutines, AsyncTask
└── DietaMainActivity.kt        # Activity principal, sensores
```

## ✅ **Todos los Conceptos Implementados**

- ✅ Tipos de datos, arreglos, Variables (var, val)
- ✅ Funciones, métodos, Clases, Métodos, Objetos
- ✅ Secuencias repetitivas for, while, if/if-else
- ✅ Eventos, Programación de eventos
- ✅ RecyclerView, BaseAdapter, ArrayAdapter
- ✅ RecyclerView.Adapter, ViewHolder, CardView
- ✅ Fragments básicos y dinámicos
- ✅ Persistencia: Files, SQLite, SQLiteOpenHelper
- ✅ Modelo MVC
- ✅ Thread, AsyncTask, runOnUiThread, JobScheduler
- ✅ Acelerómetro, sensores de luz, proximidad
- ✅ JSON, Volley, llamadas y respuestas

Esta aplicación es un ejemplo completo y funcional que demuestra todos los conceptos fundamentales del desarrollo Android moderno.