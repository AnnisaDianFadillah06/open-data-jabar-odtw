package com.example.praktikumpertama

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.praktikumpertama.ui.theme.PraktikumPertamaTheme
import com.example.praktikumpertama.ui.AppNavHost
import com.example.praktikumpertama.viewmodel.DataViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PraktikumPertamaTheme {
                // Inisialisasi ViewModel
                val dataViewModel: DataViewModel = viewModel()
                // Menampilkan Navigation Host
                AppNavHost(viewModel = dataViewModel)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PraktikumPertamaTheme {
        Greeting("Android")
    }
}