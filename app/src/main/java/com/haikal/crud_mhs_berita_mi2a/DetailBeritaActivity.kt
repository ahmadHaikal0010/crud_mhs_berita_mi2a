package com.haikal.crud_mhs_berita_mi2a

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide

class DetailBeritaActivity : AppCompatActivity() {

    private lateinit var judulDetail: TextView
    private lateinit var imgDetail: ImageView
    private lateinit var tanggalDetail: TextView
    private lateinit var isiDetail: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_berita)

        judulDetail = findViewById(R.id.judulDetail)
        imgDetail = findViewById(R.id.imgDetail)
        tanggalDetail = findViewById(R.id.tanggalDetail)
        isiDetail = findViewById(R.id.isiDetail)

        val judul = intent.getStringExtra("judul")
        val gambar = intent.getStringExtra("gambar_berita")
        val tgl = intent.getStringExtra("tgl_berita")
        val isi = intent.getStringExtra("isi_berita")

        judulDetail.text = judul
        Glide.with(this).load("http://10.126.38.166/berita/image/" + gambar).centerCrop().into(imgDetail)
        tanggalDetail.text = tgl
        isiDetail.text = isi

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}