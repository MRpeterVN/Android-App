package com.example.doan

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.doan.Adapter.PlayListAdapter
import com.example.doan.Adapter.Search_Adapter
import com.example.doan.Adapter.SongAdapter
import com.example.doan.Data.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.NonDisposableHandle.parent


class Fragment_1 : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerView3: RecyclerView
    private lateinit var nhacmoi: ImageView
    private lateinit var imageSlider: ImageSlider
    private lateinit var autoCompleteTextView: AutoCompleteTextView
    private  var items2 = mutableListOf<SongData>()
    val items = mutableListOf<PlayListData>()
    val searchDATA = mutableListOf<Search_result_Data>()


    private lateinit var searchResultLayout: View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_1, container, false)




       autoCompleteTextView = view.findViewById(R.id.search_bar)
        recyclerView = view.findViewById<RecyclerView>(R.id.rview1)
        recyclerView3 = view.findViewById<RecyclerView>(R.id.rview3)
        nhacmoi = view.findViewById<ImageView>(R.id.nhacmoi)





        recyclerView.layoutManager = LinearLayoutManager(context)



        nhacmoi.setOnClickListener {
            val  fragment3= Fragment_3()
            val dem = 1
            val bundle = Bundle().apply {
                putString("dem", dem.toString())
            }
            fragment3.arguments = bundle

            // Thực hiện thay thế Fragment hiện tại bằng Fragment đích
            fragment3.arguments = bundle
            val transaction = requireActivity().supportFragmentManager.beginTransaction()

            transaction.replace(R.id.f1, fragment3)
            transaction.addToBackStack(null)
            transaction.commit()
        }


        // get data
        val database = FirebaseDatabase.getInstance()
        val databaseReference = database.reference.child("DataSong")


        databaseReference.limitToFirst(20).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (songSnapshot in dataSnapshot.children) {
                    val ten = songSnapshot.child("song_name").getValue(String::class.java).toString()
                    val casi = songSnapshot.child("singer_name").getValue(String::class.java).toString()
                    val img = songSnapshot.child("urlImg").getValue(String::class.java).toString()

                    items.add(PlayListData(img, ten, casi))
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


        databaseReference.limitToFirst(20).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (songSnapshot in dataSnapshot.children) {
                    val tenbaihat = songSnapshot.child("song_name").getValue(String::class.java).toString()
                    val tencasi = songSnapshot.child("singer_name").getValue(String::class.java).toString()
                    val urlImg = songSnapshot.child("urlImg").getValue(String::class.java).toString()
                    items2.add(SongData(urlImg, tenbaihat, tencasi))

                    val adapter2 = SongAdapter(items2)
                    recyclerView3.adapter = adapter2
                    recyclerView3.layoutManager = LinearLayoutManager(
                        context,
                        LinearLayoutManager.VERTICAL,
                        false
                    )

                    adapter2.setOnItemClickListener(object :SongAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                        }
//                        }
                    })



                }


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Xử lý khi có lỗi xảy ra
            }
        })


        //------------------------------


//

//        imageSlider = view.findViewById<ImageSlider>(R.id.imageSlider)
//
//        val imageList = ArrayList<SlideModel>()
//
//        imageList.add(
//            SlideModel(
//                "https://i.pinimg.com/564x/de/bb/1a/debb1a8f2ea4cd66e3593b0fe5230adc.jpg",
//                "1"
//            )
//        )
//        imageList.add(
//            SlideModel(
//                "https://i.pinimg.com/564x/72/ce/42/72ce429a45ba61ba2e4c660746638de1.jpg",
//                "2"
//            )
//        )
//        imageList.add(
//            SlideModel(
//                "https://i.pinimg.com/564x/07/21/f4/0721f4854764d5e39530456c97ca7739.jpg",
//                "3"
//            )
//        )
//
//
//        imageSlider.setImageList(imageList, ScaleTypes.FIT)


//Search

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val searchDATA = mutableListOf<String>()
                for (songSnapshot in dataSnapshot.children) {
                    val song_name = songSnapshot.child("song_name").getValue(String::class.java).toString()
                    val singer_name = songSnapshot.child("singer_name").getValue(String::class.java).toString()
                    val item = "$song_name - $singer_name"
                    searchDATA.add(item)
                }

                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, searchDATA)
                autoCompleteTextView.setAdapter(adapter)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Xử lý khi có lỗi xảy ra
            }
        })




        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, searchDATA)
        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            val ten = selectedItem.substringBefore(" -")
if (ten!= null) {
    val db = FirebaseFirestore.getInstance()
            val  fragment3= Fragment_2()
            val dem = 4
            val bundle = Bundle().apply {
                putString("dem", dem.toString())
                putString("ten", ten.toString())
            }
            fragment3.arguments = bundle

            // Thực hiện thay thế Fragment hiện tại bằng Fragment đích
            fragment3.arguments = bundle
            val transaction = requireActivity().supportFragmentManager.beginTransaction()

            transaction.replace(R.id.f1, fragment3)
            transaction.addToBackStack(null)
            transaction.commit()


        }}



        return view

    }
}
