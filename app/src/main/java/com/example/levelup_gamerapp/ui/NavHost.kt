package com.example.levelup_gamerapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.levelup_gamerapp.ui.DrawerContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost() {
    val nav = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                scope = scope,
                drawerState = drawerState,
                snackbarHostState = snackbarHostState,
                onNavigate = { route ->
                    scope.launch {
                        drawerState.close()
                        nav.navigate(route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(nav.graph.startDestinationId) { saveState = true }
                        }
                    }
                }
            )
        },
        gesturesEnabled = true
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menú",
                                tint = Color(0xFF39FF14)
                            )
                        }
                    },
                    title = {
                        Text(
                            text = "LEVEL-UP GAMER",
                            color = Color(0xFF39FF14)
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Black
                    )
                )
            },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            containerColor = Color.Black
        ) { innerPadding ->
            AppNavGraph(
                nav = nav,
                innerPadding = innerPadding
            )
        }
    }
}

@Composable
private fun AppNavGraph(
    nav: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = nav,
        startDestination = "inicio",
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(Color.Black)
    ) {
        // 🔹 Pantallas actuales
        composable("inicio") { "PantallaPrincipal(nav)" }
        composable("productos") { "PantallaProductos(nav)" }
        // 🔹 Pantalla de detalle
        composable("novedades") { "PantallaNovedades()" }
        // 🔹 Rutas futuras (placeholders)
        composable("contacto") { "PantallaContacto()"}
        composable("login") { PlaceholderScreen("Pantalla Login (en desarrollo)") }
        composable("registro") { PlaceholderScreen("Pantalla Registro (en desarrollo)") }
        composable("carrito") { PantallaCarrito()}
    }
}

@Composable
fun PlaceholderScreen(texto: String) {
    Surface(color = Color.Black) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(texto, color = Color(0xFF1E90FF))
        }
    }
}