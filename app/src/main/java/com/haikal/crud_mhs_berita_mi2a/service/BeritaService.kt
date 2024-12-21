package com.haikal.crud_mhs_berita_mi2a.service

import com.haikal.crud_mhs_berita_mi2a.model.LoginResponse
import com.haikal.crud_mhs_berita_mi2a.model.RegisterResponse
import com.haikal.crud_mhs_berita_mi2a.model.ResponseBerita
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface BeritaService {
    @GET("getBerita.php")
    fun getAllBerita(): Call<ResponseBerita>

    @FormUrlEncoded
    @POST("register.php")
    fun register(
        @Field("username") username: String,
        @Field("fullname") fullname: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login.php")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<LoginResponse>
}