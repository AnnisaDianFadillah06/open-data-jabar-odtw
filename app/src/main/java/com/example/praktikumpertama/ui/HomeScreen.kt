package com.example.praktikumpertama.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.praktikumpertama.ui.components.LineChartView
import com.example.praktikumpertama.viewmodel.DataViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: DataViewModel) {
    val dataList by viewModel.dataList.observeAsState(emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface) // Warna background utama
    ) {
        // Header dengan Gradient
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF3F51B5), Color(0xFF1A237E))
                    )
                )
                .padding(16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Column {
                Text(
                    text = "Open Data Jabar",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Pantau data & statistik secara real-time!",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Statistik Ringkasan dalam Kartu
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Total Provinsi yang Dianalisis", fontWeight = FontWeight.Bold, color = Color(0xFF1A237E))
                Text(
                    text = "${dataList.size} Provinsi",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A237E)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Grafik dalam Kartu
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Grafik Data Per Provinsi",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (dataList.isNotEmpty()) {
                    LineChartView(dataList)
                } else {
                    Text(
                        text = "Data tidak tersedia",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Tombol Navigasi ke Profil
        Button(
            onClick = { navController.navigate("profile") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F51B5)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Lihat Profil", fontSize = 18.sp, color = Color.White)
        }
    }
}

