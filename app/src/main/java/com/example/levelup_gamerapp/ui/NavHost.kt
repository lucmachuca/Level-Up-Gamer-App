package com.example.levelup_gamerapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
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
import kotlinx.coroutines.CoroutineScope
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
        startDestination = "productos",
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(Color.Black)
    ) {
        composable("productos") {
            PantallaProductos(nav)
        }
        composable("inicio") {
            PlaceholderScreen("Pantalla de inicio (en desarrollo)")
        }
        composable("contacto") {
            PlaceholderScreen("Pantalla de contacto (en desarrollo)")
        }

        // 🔥 NUEVA RUTA: Detalle del producto
        composable("producto/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            if (id != null) {
                PantallaProducto(
                    id = id,
                    onNavigateBack = { nav.popBackStack() }
                )
            } else {
                PlaceholderScreen("Error: producto no encontrado")
            }
        }
    }
}

@Composable
private fun DrawerContent(
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

        DrawerItem(
            title = "Inicio",
            icon = Icons.Default.Home,
            color = Color(0xFF1E90FF)
        ) { onNavigate("inicio") }

        DrawerItem(
            title = "Productos",
            icon = Icons.Default.ShoppingCart,
            color = Color(0xFF39FF14)
        ) { onNavigate("productos") }

        DrawerItem(
            title = "Contacto",
            icon = Icons.Default.Email,
            color = Color(0xFF1E90FF)
        ) { onNavigate("contacto") }
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
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        label = { Text(title, color = color) },
        selected = false,
        onClick = onClick,
        icon = { Icon(icon, contentDescription = title, tint = color) },
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
    )
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
