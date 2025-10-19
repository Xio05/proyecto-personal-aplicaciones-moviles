package com.example.proyectopersonal.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.proyectopersonal.models.*

class DietaDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    
    companion object {
        private const val DATABASE_NAME = "dieta_database.db"
        private const val DATABASE_VERSION = 1
        
        // Tabla Usuarios
        private const val TABLE_USUARIOS = "usuarios"
        private const val COL_USER_ID = "id"
        private const val COL_USER_NOMBRE = "nombre"
        private const val COL_USER_EMAIL = "email"
        private const val COL_USER_PESO_ACTUAL = "peso_actual"
        private const val COL_USER_PESO_OBJETIVO = "peso_objetivo"
        private const val COL_USER_ALTURA = "altura"
        private const val COL_USER_EDAD = "edad"
        private const val COL_USER_GENERO = "genero"
        private const val COL_USER_NIVEL_ACTIVIDAD = "nivel_actividad"
        
        // Tabla Alimentos
        private const val TABLE_ALIMENTOS = "alimentos"
        private const val COL_ALIMENTO_ID = "id"
        private const val COL_ALIMENTO_NOMBRE = "nombre"
        private const val COL_ALIMENTO_CALORIAS = "calorias"
        private const val COL_ALIMENTO_PROTEINAS = "proteinas"
        private const val COL_ALIMENTO_CARBOHIDRATOS = "carbohidratos"
        private const val COL_ALIMENTO_GRASAS = "grasas"
        private const val COL_ALIMENTO_FIBRA = "fibra"
        private const val COL_ALIMENTO_CATEGORIA = "categoria"
        
        // Tabla Comidas Consumidas
        private const val TABLE_COMIDAS_CONSUMIDAS = "comidas_consumidas"
        private const val COL_COMIDA_ID = "id"
        private const val COL_COMIDA_ALIMENTO_ID = "alimento_id"
        private const val COL_COMIDA_CANTIDAD = "cantidad"
        private const val COL_COMIDA_FECHA = "fecha"
        private const val COL_COMIDA_TIPO = "tipo_comida"
        private const val COL_COMIDA_USER_ID = "usuario_id"
    }
    
    override fun onCreate(db: SQLiteDatabase) {
        val createUsuariosTable = """
            CREATE TABLE $TABLE_USUARIOS (
                $COL_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_USER_NOMBRE TEXT NOT NULL,
                $COL_USER_EMAIL TEXT UNIQUE NOT NULL,
                $COL_USER_PESO_ACTUAL REAL NOT NULL,
                $COL_USER_PESO_OBJETIVO REAL NOT NULL,
                $COL_USER_ALTURA INTEGER NOT NULL,
                $COL_USER_EDAD INTEGER NOT NULL,
                $COL_USER_GENERO TEXT NOT NULL,
                $COL_USER_NIVEL_ACTIVIDAD TEXT NOT NULL
            )
        """.trimIndent()
        
        val createAlimentosTable = """
            CREATE TABLE $TABLE_ALIMENTOS (
                $COL_ALIMENTO_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_ALIMENTO_NOMBRE TEXT NOT NULL,
                $COL_ALIMENTO_CALORIAS INTEGER NOT NULL,
                $COL_ALIMENTO_PROTEINAS REAL NOT NULL,
                $COL_ALIMENTO_CARBOHIDRATOS REAL NOT NULL,
                $COL_ALIMENTO_GRASAS REAL NOT NULL,
                $COL_ALIMENTO_FIBRA REAL NOT NULL,
                $COL_ALIMENTO_CATEGORIA TEXT NOT NULL
            )
        """.trimIndent()
        
        val createComidasTable = """
            CREATE TABLE $TABLE_COMIDAS_CONSUMIDAS (
                $COL_COMIDA_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_COMIDA_ALIMENTO_ID INTEGER NOT NULL,
                $COL_COMIDA_CANTIDAD REAL NOT NULL,
                $COL_COMIDA_FECHA TEXT NOT NULL,
                $COL_COMIDA_TIPO TEXT NOT NULL,
                $COL_COMIDA_USER_ID INTEGER NOT NULL,
                FOREIGN KEY($COL_COMIDA_ALIMENTO_ID) REFERENCES $TABLE_ALIMENTOS($COL_ALIMENTO_ID),
                FOREIGN KEY($COL_COMIDA_USER_ID) REFERENCES $TABLE_USUARIOS($COL_USER_ID)
            )
        """.trimIndent()
        
        db.execSQL(createUsuariosTable)
        db.execSQL(createAlimentosTable)
        db.execSQL(createComidasTable)
        
        insertarAlimentosIniciales(db)
    }
    
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_COMIDAS_CONSUMIDAS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ALIMENTOS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USUARIOS")
        onCreate(db)
    }
    
    // CRUD Operations para Alimentos
    fun insertarAlimento(alimento: Alimento): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_ALIMENTO_NOMBRE, alimento.nombre)
            put(COL_ALIMENTO_CALORIAS, alimento.calorias)
            put(COL_ALIMENTO_PROTEINAS, alimento.proteinas)
            put(COL_ALIMENTO_CARBOHIDRATOS, alimento.carbohidratos)
            put(COL_ALIMENTO_GRASAS, alimento.grasas)
            put(COL_ALIMENTO_FIBRA, alimento.fibra)
            put(COL_ALIMENTO_CATEGORIA, alimento.categoria)
        }
        return db.insert(TABLE_ALIMENTOS, null, values)
    }
    
    fun obtenerTodosLosAlimentos(): List<Alimento> {
        val alimentos = mutableListOf<Alimento>()
        val db = readableDatabase
        val cursor = db.query(TABLE_ALIMENTOS, null, null, null, null, null, COL_ALIMENTO_NOMBRE)
        
        with(cursor) {
            while (moveToNext()) {
                val alimento = Alimento(
                    id = getInt(getColumnIndexOrThrow(COL_ALIMENTO_ID)),
                    nombre = getString(getColumnIndexOrThrow(COL_ALIMENTO_NOMBRE)),
                    calorias = getInt(getColumnIndexOrThrow(COL_ALIMENTO_CALORIAS)),
                    proteinas = getDouble(getColumnIndexOrThrow(COL_ALIMENTO_PROTEINAS)),
                    carbohidratos = getDouble(getColumnIndexOrThrow(COL_ALIMENTO_CARBOHIDRATOS)),
                    grasas = getDouble(getColumnIndexOrThrow(COL_ALIMENTO_GRASAS)),
                    fibra = getDouble(getColumnIndexOrThrow(COL_ALIMENTO_FIBRA)),
                    categoria = getString(getColumnIndexOrThrow(COL_ALIMENTO_CATEGORIA))
                )
                alimentos.add(alimento)
            }
        }
        cursor.close()
        return alimentos
    }
    
    // CRUD Operations para Comidas Consumidas
    fun insertarComidaConsumida(comida: ComidaConsumida): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_COMIDA_ALIMENTO_ID, comida.alimentoId)
            put(COL_COMIDA_CANTIDAD, comida.cantidad)
            put(COL_COMIDA_FECHA, comida.fecha)
            put(COL_COMIDA_TIPO, comida.tipoComida)
            put(COL_COMIDA_USER_ID, comida.usuarioId)
        }
        return db.insert(TABLE_COMIDAS_CONSUMIDAS, null, values)
    }
    
    fun obtenerComidasPorFecha(fecha: String, usuarioId: Int): List<ComidaConsumida> {
        val comidas = mutableListOf<ComidaConsumida>()
        val db = readableDatabase
        val selection = "$COL_COMIDA_FECHA = ? AND $COL_COMIDA_USER_ID = ?"
        val selectionArgs = arrayOf(fecha, usuarioId.toString())
        val cursor = db.query(TABLE_COMIDAS_CONSUMIDAS, null, selection, selectionArgs, null, null, null)
        
        with(cursor) {
            while (moveToNext()) {
                val comida = ComidaConsumida(
                    id = getInt(getColumnIndexOrThrow(COL_COMIDA_ID)),
                    alimentoId = getInt(getColumnIndexOrThrow(COL_COMIDA_ALIMENTO_ID)),
                    cantidad = getDouble(getColumnIndexOrThrow(COL_COMIDA_CANTIDAD)),
                    fecha = getString(getColumnIndexOrThrow(COL_COMIDA_FECHA)),
                    tipoComida = getString(getColumnIndexOrThrow(COL_COMIDA_TIPO)),
                    usuarioId = getInt(getColumnIndexOrThrow(COL_COMIDA_USER_ID))
                )
                comidas.add(comida)
            }
        }
        cursor.close()
        return comidas
    }
    
    private fun insertarAlimentosIniciales(db: SQLiteDatabase) {
        val alimentosIniciales = arrayOf(
            arrayOf("Manzana", 52, 0.3, 14.0, 0.2, 2.4, "Frutas"),
            arrayOf("Plátano", 89, 1.1, 23.0, 0.3, 2.6, "Frutas"),
            arrayOf("Pechuga de Pollo", 165, 31.0, 0.0, 3.6, 0.0, "Proteínas"),
            arrayOf("Arroz Integral", 111, 2.6, 23.0, 0.9, 1.8, "Carbohidratos"),
            arrayOf("Brócoli", 34, 2.8, 7.0, 0.4, 2.6, "Verduras"),
            arrayOf("Salmón", 208, 20.0, 0.0, 13.0, 0.0, "Proteínas"),
            arrayOf("Avena", 389, 16.9, 66.0, 6.9, 10.6, "Carbohidratos"),
            arrayOf("Espinaca", 23, 2.9, 3.6, 0.4, 2.2, "Verduras")
        )
        
        for (alimento in alimentosIniciales) {
            val values = ContentValues().apply {
                put(COL_ALIMENTO_NOMBRE, alimento[0] as String)
                put(COL_ALIMENTO_CALORIAS, alimento[1] as Int)
                put(COL_ALIMENTO_PROTEINAS, alimento[2] as Double)
                put(COL_ALIMENTO_CARBOHIDRATOS, alimento[3] as Double)
                put(COL_ALIMENTO_GRASAS, alimento[4] as Double)
                put(COL_ALIMENTO_FIBRA, alimento[5] as Double)
                put(COL_ALIMENTO_CATEGORIA, alimento[6] as String)
            }
            db.insert(TABLE_ALIMENTOS, null, values)
        }
    }
}