package com.example.praktikumpertama.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star

@Composable
fun BottomBar(navController: NavController) {
    NavigationBar {
        val currentRoute = navController.currentDestination?.route

        val items = listOf(
            BottomNavItem("profile", Icons.Default.AccountCircle, "Profile"),
            BottomNavItem("list", Icons.Default.List, "List") ,
            BottomNavItem("lihat", Icons.Default.Star, "API"),
            BottomNavItem("home", Icons.Default.Home, "Home")
        )

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = false }
                    }
                }
            )
        }
    }
}

data class BottomNavItem(val route: String, val icon: ImageVector, val label: String)