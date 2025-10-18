package com.example.levelup_gamerapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "productos"
    ) {
        composable("productos") {
            PantallaProductos(navController)
        }

        // 🔹 Esta se mantiene para evitar errores de compilación al unir ramas
        composable("inicio") {
            PlaceholderScreen("Pantalla de inicio (en desarrollo)")
        }

        composable("contacto") {
            PlaceholderScreen("Pantalla de contacto (en desarrollo)")
        }
    }
}

@Composable
fun PlaceholderScreen(texto: String) {
    Surface {
        Column(
            modifier = Modifier.padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(texto)
        }
    }
}
