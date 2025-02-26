package com.example.praktikumpertama.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data_table")
data class DataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val kodeProvinsi: String,
    val namaProvinsi: String,
    val kodeKabupatenKota: String,
    val namaKabupatenKota: String,
    val total: Double,
    val satuan: String,
    val tahun: Int
)

@Entity(tableName = "profile_table")
data class ProfileEntity(
    @PrimaryKey(autoGenerate = false) // Hanya ada satu profil pengguna
    val id: Int = 1, // Default ID untuk profil
    val studentName: String,
    val studentId: String,
    val studentEmail: String
)
