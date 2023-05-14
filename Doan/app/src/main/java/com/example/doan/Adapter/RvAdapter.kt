package com.example.doan.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.doan.Data.OutData
import com.example.doan.R

class RvAdapter (var ds:List<OutData>): RecyclerView.Adapter<RvAdapter.PhimViewHolder>() {
    //    class viewholder
    inner class PhimViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtMieuTa = itemView.findViewById<TextView>(R.id.txtTenBaiHat)
        val txtTenPhim = itemView.findViewById<TextView>(R.id.txtCaSi)
        val imageView = itemView.findViewById<ImageView>(R.id.imgView)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhimViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_item,parent,false)
        return PhimViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhimViewHolder, position: Int) {
        holder.itemView.apply {
            holder.txtMieuTa.text = ds[position].mieuta
            holder.txtTenPhim.text = ds[position].tenphim
            holder.imageView.setImageResource(ds[position].image)


        }
    }

    override fun getItemCount(): Int {
        return ds.size
    }
}