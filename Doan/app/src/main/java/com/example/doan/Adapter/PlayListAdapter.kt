package com.example.doan.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.doan.Data.PlayListData
import com.example.doan.R

class PlayListAdapter (var ds:List<PlayListData>): RecyclerView.Adapter<PlayListAdapter.PlayListViewHolder>(){

    inner class PlayListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtBaiHat = itemView.findViewById<TextView>(R.id.txtTenBaiHat)
        val txtCaSi = itemView.findViewById<TextView>(R.id.txtCaSi)
        val imageView = itemView.findViewById<ImageView>(R.id.imgView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_playlist,parent,false)
        return PlayListViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayListViewHolder, position: Int) {
        holder.itemView.apply {
            holder.txtBaiHat.text = ds[position].tenbaihat
            holder.txtCaSi.text = ds[position].tencasi
            holder.imageView.setImageResource(ds[position].image)

        }
    }
    override fun getItemCount(): Int {
        return ds.size
    }
}