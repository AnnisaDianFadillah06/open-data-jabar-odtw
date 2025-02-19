package com.example.praktikumpertama.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.praktikumpertama.ui.theme.BottomBar
import com.example.praktikumpertama.viewmodel.DataViewModel
import kotlinx.coroutines.delay
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier

@Composable
fun AppNavHost(viewModel: DataViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != null && !currentRoute.startsWith("hapus")) {
                BottomBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "form",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("form") {
                DataEntryScreen(navController = navController, viewModel = viewModel)
            }
            composable("profile") {
                ProfileScreen(navController = navController)
            }
            composable("list") {
                DataListScreen(navController = navController, viewModel = viewModel)
            }
            composable(
                route = "edit/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: 0
                EditScreen(navController = navController, viewModel = viewModel, dataId = id)
            }
            composable(
                route = "hapus/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: 0

                LaunchedEffect(id) {
                    viewModel.deleteDataById(id)
                    delay(300)
                    navController.popBackStack()
                }
            }
        }
    }
}
