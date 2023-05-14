package com.example.doan

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.doan.Adapter.PlayListAdapter
import com.example.doan.Adapter.SongAdapter
import com.example.doan.Data.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class Fragment_1 : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerView3: RecyclerView
    private lateinit var imageSlider: ImageSlider
    private lateinit var autoCompleteTextView: AutoCompleteTextView
    val items2 = mutableListOf<SongData>()
    val items = mutableListOf<PlayListData>()
    val searchDATA = mutableListOf<String>()

    private lateinit var searchResultLayout: View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_1, container, false)




       autoCompleteTextView = view.findViewById(R.id.search_bar)
        recyclerView = view.findViewById<RecyclerView>(R.id.rview1)
        recyclerView3 = view.findViewById<RecyclerView>(R.id.rview3)






        recyclerView.layoutManager = LinearLayoutManager(context)

        // get data
        val database = FirebaseDatabase.getInstance()
        val databaseReference = database.reference.child("DataSong")


        databaseReference.limitToFirst(10).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (songSnapshot in dataSnapshot.children) {
                    val ten = songSnapshot.child("song_name").getValue(String::class.java).toString()
                    val casi = songSnapshot.child("singer_name").getValue(String::class.java).toString()
                    val img = songSnapshot.child("urlImg").getValue(String::class.java).toString()

                    items.add(PlayListData(img, ten, casi))
                    searchDATA.add(ten)


                    val adapter = PlayListAdapter(items)
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(
                        context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )

                }

                // Hiển thị dữ liệu
                // ...

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Xử lý khi có lỗi xảy ra
            }
        })







        recyclerView3.layoutManager = LinearLayoutManager(context)

        // get data


        databaseReference.limitToFirst(10).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (songSnapshot in dataSnapshot.children) {
                    val ten = songSnapshot.child("song_name").getValue(String::class.java).toString()
                    val casi = songSnapshot.child("singer_name").getValue(String::class.java).toString()
                    val img = songSnapshot.child("urlImg").getValue(String::class.java).toString()
                    items2.add(SongData(img, ten, casi))

                    val adapter2 = SongAdapter(items2)
                    recyclerView3.adapter = adapter2
                    recyclerView3.layoutManager = LinearLayoutManager(
                        context,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    Log.e("test", img.toString())


                }

                // Hiển thị dữ liệu
                // ...

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Xử lý khi có lỗi xảy ra
            }
        })


        //------------------------------


//

        imageSlider = view.findViewById<ImageSlider>(R.id.imageSlider)
        val imageList = ArrayList<SlideModel>()

        imageList.add(
            SlideModel(
                "https://i.pinimg.com/564x/de/bb/1a/debb1a8f2ea4cd66e3593b0fe5230adc.jpg",
                "1"
            )
        )
        imageList.add(
            SlideModel(
                "https://i.pinimg.com/564x/72/ce/42/72ce429a45ba61ba2e4c660746638de1.jpg",
                "2"
            )
        )
        imageList.add(
            SlideModel(
                "https://i.pinimg.com/564x/07/21/f4/0721f4854764d5e39530456c97ca7739.jpg",
                "3"
            )
        )


        imageSlider.setImageList(imageList, ScaleTypes.FIT)


//Search
        val search_result = mutableListOf<Search_result_Data>()
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (songSnapshot in dataSnapshot.children) {
                    val song_name = songSnapshot.child("song_name").getValue(String::class.java).toString()
                    val data = Search_result_Data(song_name)
                    search_result.add(Search_result_Data(data.toString()))

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Xử lý khi có lỗi xảy ra
            }
        })
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, searchDATA)
        autoCompleteTextView.setAdapter(adapter)

        autoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            Toast.makeText(requireContext(), "Selected: $selectedItem", Toast.LENGTH_SHORT).show()
        }



        return view

    }
}
