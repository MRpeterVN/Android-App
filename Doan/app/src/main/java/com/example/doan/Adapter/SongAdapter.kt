package com.example.doan.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.doan.Adapter.SongAdapter.SongViewHolder
import com.example.doan.Data.SongData
import com.example.doan.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.NonDisposableHandle.parent

class SongAdapter (var ds:List<SongData>): RecyclerView.Adapter<SongViewHolder>(){
    //    class viewholder
    inner class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtBaiHat = itemView.findViewById<TextView>(R.id.txtTenBaiHat)
        val txtCaSi = itemView.findViewById<TextView>(R.id.txtCaSi)
        val  imageView= itemView.findViewById<ImageView>(R.id.imgView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_song,parent,false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {

         holder.itemView.apply {
            holder.txtBaiHat.text = ds[position].tenbaihat
            holder.txtCaSi.text = ds[position].tencasi
            Picasso.get().load(ds[position].image).into(holder.imageView)

        }
    }
    override fun getItemCount(): Int {
        return ds.size
    }
}