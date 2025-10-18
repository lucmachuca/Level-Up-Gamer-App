package com.example.levelup_gamerapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavHost() {
    val nav = rememberNavController()
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val snackbarHostState = remember { SnackbarHostState() }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(scope, drawerState, nav, snackbarHostState)
        }
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
                            color = Color(0xFF39FF14),
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Black
                    )
                )
            },
            snackbarHost = { SnackbarHost(snackbarHostState) },
            containerColor = Color.Black
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                AppNavigation(nav)
            }
        }
    }
}

@Composable
fun DrawerContent(
    scope: CoroutineScope,
    drawerState: DrawerState,
    nav: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    ModalDrawerSheet(
        drawerContainerColor = Color.Black,
        drawerContentColor = Color.White,
        modifier = Modifier.width(280.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = { scope.launch { drawerState.close() } }) {
                Icon(Icons.Default.Close, contentDescription = "Cerrar menú", tint = Color(0xFF39FF14))
            }
        }

        DrawerItem("Inicio", Icons.Default.Home, nav, scope, drawerState, snackbarHostState, "productos")
        DrawerItem("Carrito", Icons.Default.ShoppingCart, nav, scope, drawerState, snackbarHostState, "carrito")
        DrawerItem("Contacto", null, nav, scope, drawerState, snackbarHostState, "contacto")
    }
}

@Composable
fun DrawerItem(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector?,
    nav: NavHostController,
    scope: CoroutineScope,
    drawerState: DrawerState,
    snackbarHostState: SnackbarHostState,
    route: String
) {
    NavigationDrawerItem(
        label = { Text(title, color = Color.White) },
        selected = false,
        onClick = {
            scope.launch {
                drawerState.close()
                nav.navigate(route)
                snackbarHostState.showSnackbar("$title seleccionado")
            }
        },
        icon = {
            icon?.let {
                Icon(it, contentDescription = title, tint = Color.White)
            }
        },
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
    )
}

/**
 * Control de las pantallas a mostrar según la ruta.
 */
@Composable
fun AppNavigation(nav: NavHostController) {
    NavHost(navController = nav, startDestination = "productos") {
        composable("productos") { PantallaProductos(nav) }
        composable("producto/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            if (id != null) {
                PantallaProducto(id = id, onNavigateBack = { nav.popBackStack() })
            }
        }
        composable("contacto") { PlaceholderScreen("Pantalla de Contacto (en desarrollo)")  }
        composable("carrito") { PlaceholderScreen("Pantalla de Carrito (en desarrollo)") }
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
            Text(texto, color = Color.White)
        }
    }
}
