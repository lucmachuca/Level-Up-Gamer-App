package com.example.levelup_gamerapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.levelup_gamerapp.local.AppDatabase
import com.example.levelup_gamerapp.local.CarritoEntity
import com.example.levelup_gamerapp.repository.CarritoRepository
import com.example.levelup_gamerapp.viewmodel.CarritoViewModel
import com.example.levelup_gamerapp.viewmodel.CarritoViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCarrito() {
    val context = androidx.compose.ui.platform.LocalContext.current
    val dao = AppDatabase.obtenerBaseDatos(context).carritoDao()
    val repo = CarritoRepository(dao)
    val viewModel: CarritoViewModel = viewModel(factory = CarritoViewModelFactory(repo))
    val carrito by viewModel.carrito.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Carrito", color = Color(0xFF39FF14)) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        },
        containerColor = Color.Black
    ) { padding ->
        if (carrito.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Tu carrito está vacío 🛒", color = Color.White)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(padding)
            ) {
                items(carrito) { item ->
                    CarritoItem(item = item, onEliminar = { viewModel.eliminar(item) })
                }

                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = { viewModel.vaciar() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF39FF14)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text("Vaciar carrito", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun CarritoItem(item: CarritoEntity, onEliminar: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF111111))
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(item.imagenUrl),
                contentDescription = item.nombreProducto,
                modifier = Modifier
                    .size(80.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(item.nombreProducto, color = Color(0xFF39FF14), fontWeight = FontWeight.Bold)
                Text("Cantidad: ${item.cantidad}", color = Color.White)
                Text("Precio: $${item.precio}", color = Color(0xFF1E90FF))
            }
            IconButton(onClick = onEliminar) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
            }
        }
    }
}
