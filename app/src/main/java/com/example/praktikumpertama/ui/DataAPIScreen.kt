package com.example.praktikumpertama.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.praktikumpertama.viewmodel.DataViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.praktikumpertama.model.WisataData

@Composable
fun DataAPIScreen(navController: NavHostController, viewModel: DataViewModel) {
    val wisataList by viewModel.wisataList

    LaunchedEffect(Unit) {
        viewModel.fetchWisataList()
    }

    Scaffold(
        topBar = {
            Text(text = "Data")
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(wisataList) { wisata ->
                WisataItem(wisata)
            }
        }
    }
}

@Composable
fun WisataItem(wisata: WisataData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Kota: ${wisata.nama_kabupaten_kota}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Jenis: ${wisata.jenis_odtw}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Jumlah: ${wisata.jumlah_odtw} ${wisata.satuan}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Tahun: ${wisata.tahun}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
