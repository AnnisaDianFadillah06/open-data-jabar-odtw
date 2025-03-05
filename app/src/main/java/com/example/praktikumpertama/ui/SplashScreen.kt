package com.example.praktikumpertama.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import com.example.praktikumpertama.R

@Composable
fun SplashScreen(navController: NavController) {
    // Efek delay sebelum pindah ke screen utama
    LaunchedEffect(Unit) {
        delay(3000) // Tampilkan selama 3 detik
        navController.navigate("form") {
            popUpTo("splash") { inclusive = true } // Hapus splash dari back stack
        }
    }

    // UI Splash Screen
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo), // Ganti dengan logo aplikasi
            contentDescription = "Logo",
            modifier = Modifier.size(150.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "OpenDataJabar",
            fontSize = 24.sp,
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = "Annisa Dian Fadillah",
            fontSize = 18.sp
        )

        Text(
            text = "231511004",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(70.dp))

        CircularProgressIndicator() // ProgressBar berbentuk lingkaran
    }
}
