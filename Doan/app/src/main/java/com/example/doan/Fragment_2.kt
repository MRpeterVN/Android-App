package com.example.doan

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.media.AudioManager
import android.media.MediaPlayer
import android.content.SharedPreferences
import android.media.AudioAttributes
import android.os.Looper
import androidx.fragment.app.FragmentManager

import android.os.Handler
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.doan.Adapter.PlayListAdapter
import com.example.doan.Data.SongData
import com.example.doan.databinding.ActivityMainBinding
import com.google.firebase.database.*
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso
import org.w3c.dom.Text
import java.util.concurrent.TimeUnit
//import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayInputStream
import java.io.FileDescriptor
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class Fragment_2: Fragment() {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var seekBar: SeekBar
    lateinit var productList:MutableList<SongData>
    lateinit var dbRef : DatabaseReference
    private lateinit var handler: Handler
    private lateinit var thoigianhientai: TextView
    private lateinit var tenbaiha: TextView
    private lateinit var tencasi: TextView
    private lateinit var tongthoigianphat: TextView
    private lateinit var musicToggleImageView: ImageView
    private lateinit var nextsong: ImageView
    private lateinit var previous_song: ImageView
    private lateinit var repeat_button: ImageView
    private lateinit var shuffle_button: ImageView
    private lateinit var image_song: RoundedImageView

    private var isMusicPlaying = false
    private var lap = false
    private var random = false
    private var abc = false

    private var mediaPlayerPosition:Int = 0

    private var param1: String? = null
    private var param2: String? = null
    private var getRate: String = ""
    private var dem: String = ""
    private var getDesc: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }





    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {



        val view = inflater.inflate(R.layout.fragment_2, container, false)

        image_song = view.findViewById<RoundedImageView>(R.id.img_albart)
        tenbaiha = view.findViewById<TextView>(R.id.song_name)
        tencasi = view.findViewById<TextView>(R.id.singer_name)
        nextsong = view.findViewById<ImageView>(R.id.next_song)
        repeat_button = view.findViewById<ImageView>(R.id.repeat_button)
        shuffle_button = view.findViewById<ImageView>(R.id.shuffle_button)
        previous_song = view.findViewById<ImageView>(R.id.previous_song)
        seekBar = view.findViewById<SeekBar>(R.id.player_seekbar)
        musicToggleImageView = view.findViewById<ImageView>(R.id.player_center_icon)
        thoigianhientai = view.findViewById<TextView>(R.id.player_current_position)
        tongthoigianphat = view.findViewById<TextView>(R.id.complete_position)

        var data = this.arguments
        var ten = data?.get("ten").toString()
        if(data == null) {


// Sử dụng mediaPlayer ở đây

            val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            getRate = sharedPref.getString("getRate", null).toString()
            getDesc = sharedPref.getString("getDesc", null).toString()
            dem = sharedPref.getString("dem", null).toString()
           abc= true

        }else {
            if(ten == null) {
                val getName = data?.get("name")
                getDesc = data?.get("desc").toString()
                getRate = data?.get("rate").toString()
                dem = data?.get("dem").toString()
                var anh = data?.get("anh").toString()

                tenbaiha.setText(getDesc.toString())
                Picasso.get().load(anh).into(image_song)
            }else {
                Toast.makeText(context, "$ten", Toast.LENGTH_SHORT).show()
            }

        }







        productList = mutableListOf()
        dbRef = FirebaseDatabase.getInstance().reference.child("DataSong")

        val currentDate = Calendar.getInstance()
        val tenDaysAgo = Calendar.getInstance()
        tenDaysAgo.add(Calendar.DATE, -10)
        val tenDaysAgoDateString = SimpleDateFormat("dd/MM/yyyy").format(tenDaysAgo.time)
        val nono = SimpleDateFormat("dd/MM/yyyy").format(currentDate.time)
        val c: Query

        if (dem == "1") {
            c = dbRef.orderByChild("ngaydang").startAt(tenDaysAgoDateString).endAt(nono)
        }

        dbRef.orderByChild("ngaydang").limitToLast(4).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                productList.clear()

                if (snapshot.exists()) {
                    for (data in snapshot.children) {
                        val dataItem = data.getValue(SongData::class.java)
                        productList.add(dataItem!!)

                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        // Khởi tạo đối tượng MediaPlayer
        mediaPlayer = MediaPlayer().apply {
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
            setAudioAttributes(audioAttributes)


                setDataSource("$getRate")

                prepareAsync()}





        mediaPlayer.setOnPreparedListener {
            // Lấy thông tin về thời gian của bài hát
            val totalTimeInMillis = mediaPlayer.duration

            // Thiết lập đối tượng TextView để hiển thị thời gian hiện tại


            // Thiết lập handler để cập nhật thời gian hiện tại
            handler = Handler()
            activity?.runOnUiThread(object : Runnable {
                override fun run() {
                    if (mediaPlayer != null) {
                        val currentPosition = mediaPlayer.currentPosition
                        val currentTimeFormatted = String.format(
                            "%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(currentPosition.toLong()),
                            TimeUnit.MILLISECONDS.toSeconds(currentPosition.toLong()) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(currentPosition.toLong())
                            )
                        )
                        thoigianhientai.text = currentTimeFormatted
                        val durationInMillis = mediaPlayer.duration
                        val durationFormatted = String.format(
                            "%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(durationInMillis.toLong()),
                            TimeUnit.MILLISECONDS.toSeconds(durationInMillis.toLong()) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(durationInMillis.toLong())
                            )
                        )
                        // display the duration somewhere in your UI
                        tongthoigianphat.text = durationFormatted
                    }
                    handler.postDelayed(this, 1000)
                }
            })

            // listen for when the MediaPlayer is prepared


            // Thiết lập đối tượng SeekBar để thể hiện thời gian phát nhạc
            seekBar.max = mediaPlayer.duration
            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        mediaPlayer.seekTo(progress)
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}

                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })

            mediaPlayer.start()
            musicToggleImageView.setImageResource(R.drawable.dung)

            musicToggleImageView.setOnClickListener {
                if (isMusicPlaying) {
                    mediaPlayer.pause()
                    musicToggleImageView.setImageResource(R.drawable.play)
                } else {
                    mediaPlayer.start()
                    musicToggleImageView.setImageResource(R.drawable.dung)
                }
                isMusicPlaying = !isMusicPlaying
            }
            // Phát nhạc

        }





        nextsong.setOnClickListener {
            // Tìm vị trí của bài hát hiện tại trong productList
            val currentSongIndex = productList.indexOfFirst { it.song_name == getDesc }

            if (currentSongIndex < 0) {
                // Không tìm thấy bài hát hiện tại trong productList
                return@setOnClickListener
            }

            if (currentSongIndex >= productList.lastIndex) {
                // Bài hát hiện tại là bài hát cuối cùng trong productList, không chuyển bài hát
                return@setOnClickListener
            }

            // Lấy bài hát tiếp theo trong productList
            val nextSong = productList[currentSongIndex + 1]
            val nextSongTitle = nextSong.song_name ?: ""
            val nextSongUrl = nextSong.urlFile ?: ""
            val nextSongImg = nextSong.urlImg ?: ""
            val nextSong_Singer_Name = nextSong.singer_name ?: ""
            mediaPlayer.pause()
            mediaPlayer.setVolume(1.0f, 1.0f)

            // Cập nhật media player và thông tin bài hát mới
            mediaPlayer.apply {
                reset()
                setDataSource(nextSongUrl)
                prepare()
                start()
                seekBar.progress = 0

            }
            getDesc = nextSongTitle
            getRate=nextSongUrl
            tenbaiha.setText(nextSongTitle)
            tencasi.setText(nextSong_Singer_Name)
            Picasso.get().load(nextSongImg).into(image_song)
        }

        previous_song.setOnClickListener {
            // Tìm vị trí của bài hát hiện tại trong productList
            val currentSongIndex = productList.indexOfFirst { it.song_name == getDesc }

            if (currentSongIndex < 0) {
                // Không tìm thấy bài hát hiện tại trong productList
                return@setOnClickListener
            }

            if (currentSongIndex == 0) {
                // Bài hát hiện tại là bài hát cuối cùng trong productList, không chuyển bài hát
                return@setOnClickListener
            }

            // Lấy bài hát tiếp theo trong productList
            val lastSong = productList[currentSongIndex - 1]
            val nextSongTitle = lastSong.song_name ?: ""
            val nextSongUrl = lastSong.urlFile ?: ""
            val nextSongImg = lastSong.urlImg ?: ""
            val nextSong_Singer_Name = lastSong.singer_name ?: ""

            // Cập nhật media player và thông tin bài hát mới
            mediaPlayer.apply {
                reset()
                setDataSource(nextSongUrl)
                prepare()
                start()
                seekBar.progress = 0

            }
            getDesc = nextSongTitle
            getRate = nextSongUrl
            tenbaiha.setText(nextSongTitle)
            tencasi.setText(nextSong_Singer_Name)
            Picasso.get().load(nextSongImg).into(image_song)

        }

        repeat_button.setOnClickListener {
            if (lap == false) {
                random = false
                lap = true
                shuffle_button.setBackgroundColor(Color.TRANSPARENT)

                repeat_button.setBackgroundColor(Color.parseColor("#FF0000"))
                mediaPlayer.setOnCompletionListener {
                    mediaPlayer.seekTo(0)
                    mediaPlayer.start()
                    seekBar.progress = 0
                }

            } else {
                lap = false
                repeat_button.setBackgroundColor(Color.TRANSPARENT)

            }


        }



        shuffle_button.setOnClickListener {
            if (random == false) {
                lap = false
                random = true
                repeat_button.setBackgroundColor(Color.TRANSPARENT)
                shuffle_button.setBackgroundColor(Color.parseColor("#FF0000"))
                mediaPlayer.setOnCompletionListener {

                    val randomInRange = (1..productList.lastIndex).random()

                    // Lấy bài hát tiếp theo trong productList
                    val ngaunhien = productList[randomInRange]
                    val nextSongTitle = ngaunhien.song_name ?: ""
                    val nextSongImg = ngaunhien.urlImg ?: ""
                    val nextSong_Singer_Name = ngaunhien.singer_name ?: ""
                    val nextSongUrl = ngaunhien.urlFile ?: ""

                    // Cập nhật media player và thông tin bài hát mới
                    mediaPlayer.apply {
                        reset()
                        setDataSource(nextSongUrl)
                        prepare()
                        start()
                        seekBar.progress = 0

                    }
                    getRate= nextSongUrl
                    getDesc = nextSongTitle
                    tenbaiha.setText(nextSongTitle)
                    tencasi.setText(nextSong_Singer_Name)
                    Picasso.get().load(nextSongImg).into(image_song)


                }
            } else {
                random = false
                shuffle_button.setBackgroundColor(Color.TRANSPARENT)

            }
        }




        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Lưu trạng thái phát nhạc trước khi rời khỏi Fragment
        outState.putInt("position", mediaPlayer.currentPosition)
        outState.putBoolean("isPlaying", mediaPlayer.isPlaying)



    }




    override fun onPause() {
        super.onPause()
        val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt("currentPosition", mediaPlayer.currentPosition)

        editor.putString("getDesc", getDesc)
        editor.putString("getRate", getRate)
        editor.putString("dem", dem)
        editor.putInt("currentPosition", mediaPlayer.currentPosition)
        editor.apply()
    }


    override fun onResume() {
        super.onResume()

        if(abc ==true) {
            val sharedPref = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

            tenbaiha.setText(getDesc.toString())
            val currentPosition = sharedPref.getInt("currentPosition", 0) ?: 0
            mediaPlayer.seekTo(currentPosition)
            seekBar.progress = currentPosition

            Toast.makeText(context, "$currentPosition", Toast.LENGTH_SHORT).show()

        }
    }



//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putString("musicLink", getRate )
//        outState.putString("nhac", getDesc )
//        // Lưu các dữ liệu khác vào Bundle outState nếu cần thiết
//    }
//
//        override fun onActivityCreated(savedInstanceState: Bundle?) {
//            super.onActivityCreated(savedInstanceState)
//            if (savedInstanceState != null) {
//                getRate = savedInstanceState.getString("musicLink", "")
//                // Restore other data if needed
//            }
//            // Start playing music with the restored musicLink
//        }




}
