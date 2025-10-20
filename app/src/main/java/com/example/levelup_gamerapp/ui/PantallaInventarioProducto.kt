package com.example.levelup_gamerapp.ui

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.levelup_gamerapp.local.AppDatabase
import com.example.levelup_gamerapp.repository.InventarioRepository
import com.example.levelup_gamerapp.viewmodel.InventarioProductosViewModel
import com.example.levelup_gamerapp.viewmodel.InventarioProductoViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaInventarioProducto() {
    val context = LocalContext.current.applicationContext as Application
    val dao = AppDatabase.getDatabase(context).inventarioDao()
    val repo = InventarioRepository(dao)
    val vm: InventarioProductosViewModel = viewModel(factory = InventarioProductoViewModelFactory(repo))

    val inventario by vm.inventario.collectAsState()
    val mensaje by vm.mensaje.collectAsState()
    var mostrarDialogo by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { vm.cargarInventario() }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { mostrarDialogo = true },
                containerColor = Color(0xFF39FF14)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar", tint = Color.Black)
            }
        },
        containerColor = Color.Black,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Inventario", color = Color(0xFF39FF14)) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Black)
            )
        }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (mensaje.isNotEmpty()) {
                Text(mensaje, color = Color(0xFF39FF14))
                Spacer(Modifier.height(8.dp))
            }

            if (inventario.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay registros de inventario.", color = Color.Gray)
                }
            } else {
                LazyColumn {
                    items(inventario) { item ->
                        Card(
                            Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF121212))
                        ) {
                            Column(Modifier.padding(12.dp)) {
                                Text("ID Producto: ${item.productoId}", color = Color(0xFF39FF14))
                                Text("Cantidad: ${item.cantidad}", color = Color.White)
                                Text("Estado: ${item.estado}", color = Color.Gray)
                                Spacer(Modifier.height(6.dp))
                                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                    TextButton(onClick = { vm.eliminarRegistro(item) }) {
                                        Text("Eliminar", color = Color.Red)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (mostrarDialogo) {
            FormInventario(
                onDismiss = { mostrarDialogo = false },
                onConfirm = { productoId, cantidad, estado ->
                    vm.agregarRegistro(productoId, cantidad, estado)
                    mostrarDialogo = false
                }
            )
        }
    }
}
