package com.example.doan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.doan.Adapter.PlayListAdapter
import com.example.doan.Adapter.RvAdapter
import com.example.doan.Adapter.SongAdapter
import com.example.doan.Data.OutData
import com.example.doan.Data.PlayListData
import com.example.doan.Data.SongData


class Fragment_3 : Fragment() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_3, container, false)

        recyclerView = view.findViewById<RecyclerView>(R.id.rview1)

        recyclerView.layoutManager = LinearLayoutManager(context)
        val items2 = mutableListOf<PlayListData>()


        val adapter = PlayListAdapter(items2)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )

        return view
    }
}