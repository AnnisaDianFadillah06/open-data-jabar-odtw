package com.example.praktikumpertama.utils

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object FileUtils {
    fun saveImageToExternalStorage(context: Context, bitmap: Bitmap, filename: String): String {
        val imagesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) // Penyimpanan aman
        val imageFile = File(imagesDir, "$filename.jpg")

        FileOutputStream(imageFile).use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        }

        return imageFile.absolutePath // Simpan path absolut ke database
    }
}
