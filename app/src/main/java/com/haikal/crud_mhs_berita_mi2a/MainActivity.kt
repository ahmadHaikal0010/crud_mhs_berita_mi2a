package com.haikal.crud_mhs_berita_mi2a

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.haikal.crud_mhs_berita_mi2a.adapter.BeritaAdapter
import com.haikal.crud_mhs_berita_mi2a.model.ModelBerita
import com.haikal.crud_mhs_berita_mi2a.model.ResponseBerita
import com.haikal.crud_mhs_berita_mi2a.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var recyclerview: RecyclerView
    private lateinit var call: Call<ResponseBerita>
    private lateinit var beritaAdapter: BeritaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        swipeRefresh = findViewById(R.id.refresh_layout)
        recyclerview = findViewById(R.id.rv_berita)

        beritaAdapter = BeritaAdapter { modelBerita: ModelBerita -> beritaOnClick(modelBerita) }

        recyclerview.adapter = beritaAdapter
        recyclerview.layoutManager = LinearLayoutManager(
            applicationContext, LinearLayoutManager.VERTICAL,
            false
        )
        swipeRefresh.setOnRefreshListener {
            getData()
        }
        getData()
    }

    private fun beritaOnClick(modelBerita: ModelBerita) {
        Toast.makeText(
            applicationContext, modelBerita.judul,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun getData() {
        swipeRefresh.isRefreshing = true
        call = ApiClient.retrofit.getAllBerita()
        call.enqueue(object : Callback<ResponseBerita> {
            override fun onResponse(
                call: Call<ResponseBerita>,
                response: Response<ResponseBerita>
            ) {
                swipeRefresh.isRefreshing = false
                if (response.isSuccessful) {
                    beritaAdapter.submitList(response.body()?.data )
                    beritaAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ResponseBerita>, t: Throwable) {
                swipeRefresh.isRefreshing = false
                Toast.makeText(
                    applicationContext, t.localizedMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }
}