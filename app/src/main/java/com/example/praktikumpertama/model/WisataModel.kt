package com.example.praktikumpertama.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("message") val message: String,
    @SerializedName("error") val error: Int,
    @SerializedName("data") val data: List<WisataData>
)

data class WisataData(
    val id: Int,
    val kode_provinsi: Int,
    val nama_provinsi: String,
    val kode_kabupaten_kota: Int,
    val nama_kabupaten_kota: String,
    val jenis_odtw: String,
    val jumlah_odtw: Int,
    val satuan: String,
    val tahun: Int
)