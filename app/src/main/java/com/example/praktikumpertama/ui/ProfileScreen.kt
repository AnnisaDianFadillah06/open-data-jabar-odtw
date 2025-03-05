package com.example.praktikumpertama.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.praktikumpertama.ui.themejetsnack.JetsnackTheme
import com.example.praktikumpertama.viewmodel.DataViewModel
import com.example.praktikumpertama.ui.components.JetsnackButton

@Composable
fun ProfileScreen(navController: NavHostController, viewModel: DataViewModel) {
    val profile by viewModel.profile.observeAsState()

    var isEditing by remember { mutableStateOf(false) }
    var studentName by remember { mutableStateOf(profile?.studentName ?: "") }
    var studentId by remember { mutableStateOf(profile?.studentId ?: "") }
    var studentEmail by remember { mutableStateOf(profile?.studentEmail ?: "") }
    var profileUploaded by remember { mutableStateOf(false) }

    LaunchedEffect(profile) {
        profile?.let {
            studentName = it.studentName
            studentId = it.studentId
            studentEmail = it.studentEmail
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
                    .background(Color.LightGray, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = if (profileUploaded) "Uploaded Profile Picture" else "Default Profile Picture",
                    tint = if (profileUploaded) MaterialTheme.colorScheme.onPrimary else Color.Gray,
                    modifier = Modifier.size(80.dp)
                )
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

                JetsnackTheme { JetsnackButton(
                    onClick = {
                        viewModel.updateProfile(studentName, studentId, studentEmail)
                        isEditing = false
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save")
                }}
            } else {
                Text(text = studentName, style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "ID: $studentId", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = studentEmail, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(16.dp))

                JetsnackTheme { JetsnackButton(
                    onClick = { isEditing = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Edit Profile")
                }}
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tombol kembali
            JetsnackTheme { JetsnackButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back")
            }}
        }
    }
}

