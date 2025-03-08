package com.example.praktikumpertama.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import com.airbnb.lottie.compose.*
import com.example.praktikumpertama.R
import com.example.praktikumpertama.R.raw.splashlogo

@Composable
fun SplashScreen(navController: NavController) {
    // Efek delay sebelum pindah ke home
    LaunchedEffect(Unit) {
        delay(5000) // Tampilkan selama 2 detik
        navController.navigate("onboarding") {
            popUpTo("splash") { inclusive = true } // Hapus SplashScreen dari tumpukan navigasi
        }
    }

    // Animasi Lottie
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(splashlogo))
    val progress by animateLottieCompositionAsState(composition)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(200.dp) // Sesuaikan ukuran logo
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

        // **Progress Bar (Linear)**
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .width(200.dp)
                .height(8.dp), // Atur tinggi progress bar
            color = MaterialTheme.colorScheme.primary
        )
    }
}
