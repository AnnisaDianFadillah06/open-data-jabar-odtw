package com.example.praktikumpertama.viewmodel

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.praktikumpertama.data.AppDatabase
import com.example.praktikumpertama.data.DataEntity
import com.example.praktikumpertama.data.ProfileEntity
import com.example.praktikumpertama.utils.FileUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).dataDao()
    val dataList: LiveData<List<DataEntity>> = dao.getAll()
    private val profileDao = AppDatabase.getDatabase(application).profileDao()
    val profile: LiveData<ProfileEntity?> = profileDao.getProfile()

    fun updateProfile(name: String, id: String, email: String, bitmap: Bitmap?) {
        viewModelScope.launch {
            val currentProfile = profileDao.getProfileNow() // Ambil data lama

            val imagePath = if (bitmap != null) {
                FileUtils.saveImageToExternalStorage(getApplication(), bitmap, "profile_$id") // Simpan gambar baru
            } else {
                currentProfile?.photoUri ?: "" // Gunakan path lama atau kosong
            }

            val newProfile = ProfileEntity(
                studentName = name,
                studentId = id,
                studentEmail = email,
                photoUri = imagePath
            )
            profileDao.insertOrUpdate(newProfile)
        }
    }




    fun updatePhoto(photoUri: String) {
            viewModelScope.launch {
                profileDao.updatePhoto(photoUri)
            }
        }



    fun insertData(
        kodeProvinsi: String,
        namaProvinsi: String,
        kodeKabupatenKota: String,
        namaKabupatenKota: String,
        total: String,
        satuan: String,
        tahun: String
    ) {
        viewModelScope.launch {
            val totalValue = total.toDoubleOrNull() ?: 0.0
            val tahunValue = tahun.toIntOrNull() ?: 0
            dao.insert(
                DataEntity(
                    kodeProvinsi = kodeProvinsi,
                    namaProvinsi = namaProvinsi,
                    kodeKabupatenKota = kodeKabupatenKota,
                    namaKabupatenKota = namaKabupatenKota,
                    total = totalValue,
                    satuan = satuan,
                    tahun = tahunValue
                )
            )
        }
    }

    fun updateData(data: DataEntity) {
        viewModelScope.launch {
            dao.update(data)
        }
    }

    suspend fun getDataById(id: Int): DataEntity? {
        return withContext(Dispatchers.IO) {
            dao.getById(id)
        }
    }

    fun deleteDataById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }

}