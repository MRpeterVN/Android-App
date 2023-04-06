package com.example.doan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.doan.Adapter.RvAdapter
import com.example.doan.Adapter.SongAdapter
import com.example.doan.Data.OutData
import com.example.doan.Data.SongData


class Fragment_1 : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerView2: RecyclerView
    private lateinit var recyclerView3: RecyclerView
    private lateinit var imageSlider: ImageSlider




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_1, container, false)

        recyclerView = view.findViewById<RecyclerView>(R.id.rview1)
        recyclerView2 = view.findViewById<RecyclerView>(R.id.rview2)
        recyclerView3 = view.findViewById<RecyclerView>(R.id.rview3)


        recyclerView.layoutManager = LinearLayoutManager(context)
        val items = mutableListOf<OutData>()
        items.add(OutData(R.drawable.chillhop_vol2_nl, "Anh Là Dân Chơi", "KHOA, Hải Ngân"))
        items.add(OutData(R.drawable.chillhop_vol2_nl, "Anh Là Dân Chơi,Ghệ Iu Dấu Của Em Ơi (Sped Up)", "KHOA, Hải Ngân"))
        items.add(OutData(R.drawable.chillhop_vol2_nl, "Anh Là Dân Chơi,Ghệ Iu Dấu Của Em Ơi (Sped Up)", "KHOA, Hải Ngân"))
        items.add(OutData(R.drawable.chillhop_vol2_nl, "Anh Là Dân Chơi,Ghệ Iu Dấu Của Em Ơi (Sped Up)", "KHOA, Hải Ngân"))
        items.add(OutData(R.drawable.chillhop_vol2_nl, "Anh Là Dân Chơi,Ghệ Iu Dấu Của Em Ơi (Sped Up)", "KHOA, Hải Ngân"))
        items.add(OutData(R.drawable.chillhop_vol2_nl, "Anh Là Dân Chơi", "KHOA, Hải Ngân"))

        val adapter = RvAdapter(items)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )


        recyclerView2.layoutManager = LinearLayoutManager(context)
        val items1 = mutableListOf<OutData>()
        items1.add(OutData(R.drawable.chillhop_vol2_nl, "Anh Là Dân Chơi", "KHOA, Hải Ngân"))
        items1.add(OutData(R.drawable.chillhop_vol2_nl, "Anh Là Dân Chơi", "KHOA, Hải Ngân"))
        items1.add(OutData(R.drawable.chillhop_vol2_nl, "Anh Là Dân Chơi", "KHOA, Hải Ngân"))
        items1.add(OutData(R.drawable.chillhop_vol2_nl, "Anh Là Dân Chơi", "KHOA, Hải Ngân"))

        val adapter1 = RvAdapter(items)
        recyclerView2.adapter = adapter1
        recyclerView2.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        recyclerView3.layoutManager = LinearLayoutManager(context)
        val items2 = mutableListOf<SongData>()
        items2.add(SongData(R.drawable.chillhop_vol2_nl, "Anh Là Dân Chơi Anh Là Dân Chơi", "KHOA, Hải Ngân"))
        items2.add(SongData(R.drawable.chillhop_vol2_nl, "Không Yêu Trả Dép Tôi Về ", "HuyR"))
        items2.add(SongData(R.drawable.chillhop_vol2_nl, "Ghệ Iu Dấu Của Em Ơi (Sped Up)", "tlinh, 2pillz, WOKEUP"))
        items2.add(SongData(R.drawable.chillhop_vol2_nl, "Lệ Cay", "Du Thiên"))
        items2.add(SongData(R.drawable.chillhop_vol2_nl, "Cầu Vồng Sau Mưa", "Trường An"))


        val adapter2 = SongAdapter(items2)
        recyclerView3.adapter = adapter2
        recyclerView3.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )




//

        imageSlider = view.findViewById<ImageSlider>(R.id.imageSlider)
        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel("https://cdn.luatminhkhue.vn/lmk/article/Roll-Safe-meme.jpg","SOk"))
        imageList.add(SlideModel("https://cdn.luatminhkhue.vn/lmk/article/e874aca8f1e818b641f9.jpg","khong the tin noi"))
        imageList.add(SlideModel("https://cdn.luatminhkhue.vn/lmk/article/download%20(7)(1).jpg","Khong dua"))


        imageSlider.setImageList(imageList, ScaleTypes.FIT)

        return view
    }
}
