package com.example.persistenciagatitos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var btnAgregar: Button
    private lateinit var dbHandler: DatabaseHelper
    private lateinit var btnBorrar: Button
    private lateinit var btnActualizar: Button
    private lateinit var etID: EditText
    private lateinit var etFechaNacimiento: EditText
    private lateinit var etRaza: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        etNombre = findViewById(R.id.editTextNombre)
        etFechaNacimiento = findViewById(R.id.editTextFecha)
        etRaza = findViewById(R.id.editTextRaza)
        btnAgregar = findViewById(R.id.buttonCrear)
        btnBorrar = findViewById(R.id.buttonBorrar)
        btnActualizar = findViewById(R.id.buttonActualizar)


        dbHandler = DatabaseHelper(this)
        etID = findViewById(R.id.editTextID)


        val buttonVerGatos = findViewById<Button>(R.id.buttonVerGatos)

        btnAgregar.setOnClickListener { addGato() }
        btnBorrar.setOnClickListener { borrarGato() }
        btnActualizar.setOnClickListener { actualizarGato() }



        buttonVerGatos.setOnClickListener {
            intent = Intent(this, GatoLista::class.java)
            startActivity(intent)
        }



    }

    private fun actualizarGato() {
        val nombre = etNombre.text.toString()
        val id = etID.text.toString()
        val fechaNacimiento = etFechaNacimiento.text.toString()
        val raza = etRaza.text.toString()

        if (nombre.isNotEmpty() && id.isNotEmpty() && fechaNacimiento.isNotEmpty() && raza.isNotEmpty()) {
            val gato = Gato(nombre = nombre, fechaNacimiento = fechaNacimiento, raza = raza, id = id.toInt())
            val status = dbHandler.updateGato(gato)
            if (status > -1) {
                Toast.makeText(applicationContext, "Gato actualizado", Toast.LENGTH_LONG).show()
                clearEditTexts()
            }
        } else {
            Toast.makeText(applicationContext, "usa todos los campos", Toast.LENGTH_LONG).show()
        }
    }

    private fun borrarGato() {
        val id = etID.text.toString()
        if (id.isNotEmpty()) {
            val gato = Gato(id = id.toInt())
            val status = dbHandler.deleteGato(gato)
            if (status > -1) {
                Toast.makeText(applicationContext, "Gato eliminado", Toast.LENGTH_LONG).show()
                clearEditTexts()
            }else {
                Toast.makeText(applicationContext, "id es requerido", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun addGato() {
        val id = etID.text.toString()
        val nombre = etNombre.text.toString()
        val fechaNacimiento = etFechaNacimiento.text.toString()
        val raza = etRaza.text.toString()

        if (id.isNotEmpty()){
            Toast.makeText(this, "El id debe estar vacio", Toast.LENGTH_SHORT).show()
        } else if (nombre.isNotEmpty() && fechaNacimiento.isNotEmpty() && raza.isNotEmpty()) {
            val gato = Gato(nombre = nombre, fechaNacimiento = fechaNacimiento, raza = raza)
            val status = dbHandler.addGato(gato)
            if (status > -1) {
                Toast.makeText(applicationContext, "Gato agregado", Toast.LENGTH_LONG).show()
                clearEditTexts()
            }
        } else {
            Toast.makeText(applicationContext, "use los campos", Toast.LENGTH_LONG).show()
        }
    }


    private fun clearEditTexts() {
        etNombre.text.clear()
        etID.text.clear()
        etFechaNacimiento.text.clear()
        etRaza.text.clear()
    }
}