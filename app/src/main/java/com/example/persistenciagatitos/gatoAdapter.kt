package com.example.persistenciagatitos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GatosAdapter(private val gatoList: List<Gato>) : RecyclerView.Adapter<GatosAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nombreGato: TextView = view.findViewById(R.id.tvNombreGato)
        var fechaNacimiento: TextView = view.findViewById(R.id.tvFechaNacimiento)
        var raza: TextView = view.findViewById(R.id.tvRaza)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.gatoitem, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val gato = gatoList[position]
        holder.nombreGato.text = gato.nombre
        holder.fechaNacimiento.text = gato.fechaNacimiento
        holder.raza.text = gato.raza
    }

    override fun getItemCount() = gatoList.size
}