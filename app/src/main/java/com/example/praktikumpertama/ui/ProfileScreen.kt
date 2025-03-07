package com.example.praktikumpertama.ui

import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.praktikumpertama.ui.themejetsnack.JetsnackTheme
import com.example.praktikumpertama.viewmodel.DataViewModel
import com.example.praktikumpertama.ui.components.JetsnackButton
import com.example.praktikumpertama.utils.FileUtils

@Composable
fun ProfileScreen(navController: NavHostController, viewModel: DataViewModel) {
    val profile by viewModel.profile.observeAsState()

    var isEditing by remember { mutableStateOf(false) }
    var studentName by remember { mutableStateOf(profile?.studentName ?: "") }
    var studentId by remember { mutableStateOf(profile?.studentId ?: "") }
    var studentEmail by remember { mutableStateOf(profile?.studentEmail ?: "") }
    var photoUri by remember { mutableStateOf(profile?.photoUri ?: "") }

    val context = LocalContext.current
    var selectedUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedUri = uri // Simpan URI sementara
    }

    LaunchedEffect(selectedUri) {
        selectedUri?.let { uri ->
            val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri) // Konversi ke bitmap
            val savedPath = FileUtils.saveImageToExternalStorage(context, bitmap, "profile_image") // Simpan gambar

            photoUri = savedPath // Simpan path absolut
            viewModel.updatePhoto(savedPath) // Simpan ke database
        }
    }



    LaunchedEffect(profile) {
        profile?.let {
            studentName = it.studentName
            studentId = it.studentId
            studentEmail = it.studentEmail
            photoUri = it.photoUri ?: "" // Pastikan photoUri selalu diupdate
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Profile Image Section
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    .background(Color.LightGray, CircleShape)
                    .clickable { launcher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                if (photoUri.isNotEmpty()) {
                    AsyncImage(
                        model = photoUri,
                        contentDescription = "Profile Picture",
                        modifier = Modifier.size(120.dp).clip(CircleShape)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Default Profile Picture",
                        tint = Color.Gray,
                        modifier = Modifier.size(80.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isEditing) {
                OutlinedTextField(
                    value = studentName,
                    onValueChange = { studentName = it },
                    label = { Text("Student Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = studentId,
                    onValueChange = { studentId = it },
                    label = { Text("Student ID") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = studentEmail,
                    onValueChange = { studentEmail = it },
                    label = { Text("Student Email") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                val context = LocalContext.current

                Button(
                    onClick = {
                        val bitmap: Bitmap? = if (photoUri.isNotEmpty()) {
                            try {
                                MediaStore.Images.Media.getBitmap(context.contentResolver, Uri.parse(photoUri))
                            } catch (e: Exception) {
                                null
                            }
                        } else null

                        if (bitmap != null) {
                            viewModel.updateProfile(studentName, studentId, studentEmail, bitmap)
                        } else {
                            viewModel.updateProfile(studentName, studentId, studentEmail, null)
                        }

                        isEditing = false
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save")
                }

            } else {
                Text(text = studentName, style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "ID: $studentId", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = studentEmail, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { isEditing = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Edit Profile")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tombol kembali
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back")
            }
        }
    }
}


