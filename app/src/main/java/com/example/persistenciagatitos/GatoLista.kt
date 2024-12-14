package com.example.persistenciagatitos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GatoLista : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var mostrarButton: Button
    private lateinit var dbHandler: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.listagatos)

        recyclerView = findViewById(R.id.listViewGatos)
        dbHandler = DatabaseHelper(this)
        mostrarButton = findViewById(R.id.buttonMostrar)
        val buttonVolver = findViewById<Button>(R.id.buttonVolver)

        viewGatos()

        buttonVolver.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        mostrarButton.setOnClickListener {
            viewGatos()
        }
    }

    private fun viewGatos() {
        val discosList = dbHandler.getAllGatitos()
        val adapter = GatosAdapter(discosList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }


}