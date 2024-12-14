package com.example.persistenciagatitos

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "GatitosBonitos"
        private const val DATABASE_VERSION = 2
        private const val TABLE_GATOS = "Gatos"
        private const val KEY_ID = "id"
        private const val KEY_NOMBRE = "nombre"
        private const val KEY_FECHA = "fechaNacimiento"
        private const val KEY_RAZA = "raza"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createGatosTable = ("CREATE TABLE " + TABLE_GATOS + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_NOMBRE + " TEXT, "
                + KEY_FECHA + " TEXT, "
                + KEY_RAZA + " TEXT)")
        db.execSQL(createGatosTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE $TABLE_GATOS ADD COLUMN $KEY_RAZA TEXT")
        }
    }


    fun getAllGatitos(): ArrayList<Gato> {
        val gatosList = ArrayList<Gato>()
        val selectQuery = "SELECT  * FROM $TABLE_GATOS"

        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var nombre: String
        var fechaNacimiento: String
        var raza: String

        if (cursor.moveToFirst()) {
            do {
                val idIndex = cursor.getColumnIndex(KEY_ID)
                val nombreIndex = cursor.getColumnIndex(KEY_NOMBRE)
                val fechaNacimientoIndex = cursor.getColumnIndex(KEY_FECHA)
                val razaIndex = cursor.getColumnIndex(KEY_RAZA)


                if (idIndex != -1 && nombreIndex != -1 && fechaNacimientoIndex != -1 && razaIndex != -1) {
                    val id = cursor.getInt(idIndex)
                    val nombre = cursor.getString(nombreIndex) ?: "Desconocido"
                    val fechaNacimiento = cursor.getString(fechaNacimientoIndex) ?: "Fecha desconocida"
                    val raza = cursor.getString(razaIndex) ?: "Raza desconocida"

                    val gato = Gato(id = id, nombre = nombre, fechaNacimiento = fechaNacimiento, raza = raza)
                    gatosList.add(gato)
                } else {
                    Log.e("DatabaseError", "Al menos una de las columnas no existe en la tabla.")
                }
            } while (cursor.moveToNext())
        }


        // Cierra el cursor para liberar recursos.
        cursor.close()
        return gatosList
    }

    fun updateGato(Gato: Gato): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NOMBRE, Gato.nombre)
        contentValues.put(KEY_FECHA, Gato.fechaNacimiento)
        contentValues.put(KEY_RAZA, Gato.raza)

        return db.update(TABLE_GATOS, contentValues, "$KEY_ID = ?", arrayOf(Gato.id.toString()))
    }

    fun deleteGato(Gato: Gato): Int {
        val db = this.writableDatabase
        val success = db.delete(TABLE_GATOS, "$KEY_ID = ?", arrayOf(Gato.id.toString()))
        db.close()
        return success
    }

    fun addGato(Gato: Gato): Long {
        try {
            val db = this.writableDatabase
            val contentValues = ContentValues()
            contentValues.put(KEY_NOMBRE, Gato.nombre)
            contentValues.put(KEY_FECHA, Gato.fechaNacimiento)
            contentValues.put(KEY_RAZA, Gato.raza)

            val success = db.insert(TABLE_GATOS, null, contentValues)
            db.close()
            return success
        } catch (e: Exception) {
            Log.e("Error", "Error al agregar gato", e)
            return -1
        }
    }
}