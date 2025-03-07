package com.example.praktikumpertama.network

import com.example.praktikumpertama.model.ApiResponse
import retrofit2.http.GET
import retrofit2.Call

interface ApiService {
    @GET("disparbud/od_15387_jml_ptns_obyek_daya_tarik_wisata_odtw__jenis_kabup_v2")
    fun getWisataList(): Call<ApiResponse>
}
