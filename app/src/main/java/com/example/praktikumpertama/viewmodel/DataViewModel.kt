package com.example.praktikumpertama.viewmodel

import android.app.Application
import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.praktikumpertama.data.AppDatabase
import com.example.praktikumpertama.data.DataEntity
import com.example.praktikumpertama.data.ProfileEntity
import com.example.praktikumpertama.repository.WisataRepository
import com.example.praktikumpertama.utils.FileUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.await
import androidx.compose.runtime.State
import com.example.praktikumpertama.model.WisataData


class DataViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).dataDao()
    val dataList: LiveData<List<DataEntity>> = dao.getAll()
    private val profileDao = AppDatabase.getDatabase(application).profileDao()
    val profile: LiveData<ProfileEntity?> = profileDao.getProfile()

    private val repository = WisataRepository()

    private val _wisataList = mutableStateOf<List<WisataData>>(emptyList())
    val wisataList: State<List<WisataData>> get() = _wisataList

    fun fetchWisataList() {
        viewModelScope.launch {
            try {
                val response = repository.getWisataList().await()
                if (response.error == 0) {
                    _wisataList.value = response.data.map { apiData ->
                        WisataData(
                            id = apiData.id,
                            kode_provinsi = apiData.kode_provinsi,
                            nama_provinsi = apiData.nama_provinsi,
                            kode_kabupaten_kota = apiData.kode_kabupaten_kota,
                            nama_kabupaten_kota = apiData.nama_kabupaten_kota,
                            jenis_odtw = apiData.jenis_odtw,
                            jumlah_odtw = apiData.jumlah_odtw,
                            satuan = apiData.satuan,
                            tahun = apiData.tahun
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


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