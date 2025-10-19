package com.example.levelup_gamerapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DrawerContent(
    scope: CoroutineScope,
    drawerState: DrawerState,
    snackbarHostState: SnackbarHostState,
    onNavigate: (String) -> Unit
) {
    ModalDrawerSheet(
        drawerContainerColor = Color(0xFF0A0A0A),
        drawerContentColor = Color.White
    ) {
        RowTopClose(scope, drawerState)

        Text(
            text = "LEVEL-UP GAMER",
            color = Color(0xFF39FF14),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            style = MaterialTheme.typography.titleLarge
        )

        // 🟢 Bloque principal
        DrawerItem("Inicio", "inicio", Color(0xFF1E90FF), onNavigate)
        DrawerItem("Productos", "productos", Color(0xFF39FF14), onNavigate)
        DrawerItem("Novedades", "novedades", Color(0xFF1E90FF), onNavigate)
        DrawerItem("Contacto", "contacto", Color(0xFFFFA500), onNavigate)
        DrawerItem("Carrito", "carrito", Color(0xFFFFA500), onNavigate)

        // 🔻 Separador visual
        Spacer(modifier = Modifier.height(32.dp))
        Divider(color = Color(0xFF222222), thickness = 1.dp)
        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Bloque inferior (Login / Registro)
        DrawerItem("Login", "login", Color(0xFF1E90FF), onNavigate)
        DrawerItem("Registro", "registro", Color(0xFFE91E63), onNavigate)
    }
}

@Composable
private fun RowTopClose(scope: CoroutineScope, drawerState: DrawerState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { scope.launch { drawerState.close() } }) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Cerrar menú",
                tint = Color(0xFF39FF14)
            )
        }
    }
}

@Composable
private fun DrawerItem(
    title: String,
    route: String,
    color: Color,
    onNavigate: (String) -> Unit
) {
    NavigationDrawerItem(
        label = { Text(title, color = color) },
        selected = false,
        onClick = { onNavigate(route) },
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
    )
}