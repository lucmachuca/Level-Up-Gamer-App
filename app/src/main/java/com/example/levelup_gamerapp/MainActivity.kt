
package com.example.levelup_gamerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.levelup_gamerapp.ui.AppNavHost
import com.example.levelup_gamerapp.ui.theme.LevelUp_GamerAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LevelUp_GamerAppTheme {
                AppNavHost() // ✅ Carga la navegación completa (con Drawer y todas las pantallas)
            }
        }
    }
}