package com.example.praktikumpertama.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
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
            .padding(16.dp)
    ) {
        Text(
            text = "Grafik Data Per Provinsi",
            fontSize = 20.sp,  // Tidak perlu androidx.compose.ui.unit.sp
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (dataList.isNotEmpty()) {
            LineChartView(dataList)
        } else {
            Text(
                text = "Data tidak tersedia",
                fontSize = 16.sp,  // Tidak perlu androidx.compose.ui.unit.sp
                color = Color.Gray
            )
        }
    }
}
