package com.haikal.crud_mhs_berita_mi2a.screen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.haikal.crud_mhs_berita_mi2a.MainActivity
import com.haikal.crud_mhs_berita_mi2a.R
import com.haikal.crud_mhs_berita_mi2a.model.LoginResponse
import com.haikal.crud_mhs_berita_mi2a.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // deklarasi widget
        val etUsername: EditText = findViewById(R.id.etUsernameLogin)
        val etPassword: EditText = findViewById(R.id.etPasswordLogin)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        val txtSignUp: TextView = findViewById(R.id.txtSignUp)

        txtSignUp.setOnClickListener() {
            val toRegister = Intent(this@LoginActivity, RegisterScreenActivity::class.java)

            startActivity(toRegister)
        }

        btnLogin.setOnClickListener() {
            val username = etUsername.toString()
            val password = etPassword.toString()

            // validasi input kosong
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this@LoginActivity,
                    "Username atau Password kosong!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                ApiClient.retrofit.login(username, password).enqueue(
                    object: Callback<LoginResponse> {
                        override fun onResponse(
                            call: Call<LoginResponse>,
                            response: Response<LoginResponse>
                        ) {
                            if (response.isSuccessful) {
                                val loginResponse = response.body()
                                if (loginResponse != null && response.isSuccessful) {
                                    // login berhasil
                                    Toast.makeText(
                                        this@LoginActivity,
                                        "Login Success",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    //mau pindah ke page lain
                                    val toMain = Intent(this@LoginActivity, MainActivity::class.java)
                                    startActivity(toMain)
                                }
                            } else {
                                val errorMessage = response.errorBody()?.string() ?: "Unknown Error"
                                Log.e("Login Error", errorMessage)
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Login Failure",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            Toast.makeText(
                                this@LoginActivity,
                                t.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                )
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}