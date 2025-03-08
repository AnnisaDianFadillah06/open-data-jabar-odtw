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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.praktikumpertama.model.WisataData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataAPIScreen(navController: NavHostController, viewModel: DataViewModel) {
    val wisataList by viewModel.wisataList
    val isLoading by viewModel.isLoading // Ambil state loading dari ViewModel

    LaunchedEffect(Unit) {
        viewModel.fetchWisataList()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Objek Daya Tarik Wisata",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp) // Padding kiri agar tidak terlalu mepet
                    )
                },
                modifier = Modifier.padding(vertical = 8.dp) // Padding atas & bawah
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator() // Menampilkan loading indicator
            } else {
                LazyColumn {
                    items(wisataList) { wisata ->
                        WisataItem(wisata, viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun WisataItem(wisata: WisataData, viewModel: DataViewModel) {
    val isFavorite by viewModel.isFavorite(wisata).collectAsState(initial = false)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Kota: ${wisata.nama_kabupaten_kota}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Jenis: ${wisata.jenis_odtw}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Jumlah: ${wisata.jumlah_odtw} ${wisata.satuan}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Tahun: ${wisata.tahun}", style = MaterialTheme.typography.bodyMedium)
            }

            Icon(
                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "Favorite Icon",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { viewModel.toggleFavorite(wisata) },
                tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
