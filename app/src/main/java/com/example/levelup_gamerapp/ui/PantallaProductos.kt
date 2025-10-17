package com.example.levelup_gamerapp.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.levelup_gamerapp.local.AppDatabase
import com.example.levelup_gamerapp.local.ProductosEntity
import com.example.levelup_gamerapp.repository.ProductosRepository
import com.example.levelup_gamerapp.viewmodel.ProductosViewModel
import com.example.levelup_gamerapp.viewmodel.ProductosViewModelFactory
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaProductos() {
    val context = androidx.compose.ui.platform.LocalContext.current
    val dao = AppDatabase.obtenerBaseDatos(context).productosDao()
    val repository = ProductosRepository(dao)
    val viewModel: ProductosViewModel = viewModel(factory = ProductosViewModelFactory(repository))
    val listaProductos by viewModel.productos.collectAsState()

    // Estado para controlar si se muestra el detalle
    var productoSeleccionado by remember { mutableStateOf<ProductosEntity?>(null) }

    // Insertar ejemplos una sola vez
    LaunchedEffect(Unit) {
        viewModel.insertarEjemploSiVacio()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (productoSeleccionado == null) "Productos" else "Detalle del Producto",
                        color = Color(0xFF39FF14),
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black),
                navigationIcon = {
                    if (productoSeleccionado != null) {
                        IconButton(onClick = { productoSeleccionado = null }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Volver",
                                tint = Color(0xFF39FF14)
                            )
                        }
                    }
                }
            )
        },
        containerColor = Color.Black
    ) { padding ->
        if (productoSeleccionado == null) {
            // 🔹 Lista de productos
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(padding)
            ) {
                if (listaProductos.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No hay productos disponibles", color = Color.White)
                    }
                } else {
                    Text(
                        text = "Lo más vendido esta semana",
                        fontSize = 16.sp,
                        color = Color(0xFF1E90FF),
                        modifier = Modifier.padding(start = 16.dp, bottom = 12.dp)
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(listaProductos) { producto ->
                            ProductoCard(producto) {
                                productoSeleccionado = producto
                            }
                        }
                    }
                }
            }
        } else {
            // 🔹 Vista de detalle
            PantallaDetalleProducto(productoSeleccionado!!)
        }
    }
}

@Composable
fun ProductoCard(producto: ProductosEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(230.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF111111))
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(producto.imagenUrl),
                contentDescription = producto.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(producto.nombre, color = Color(0xFF39FF14), fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text("$${producto.precio}", color = Color(0xFF1E90FF), fontSize = 12.sp)
        }
    }
}

@Composable
fun PantallaDetalleProducto(producto: ProductosEntity) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(producto.imagenUrl),
            contentDescription = producto.nombre,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = producto.nombre,
            color = Color(0xFF39FF14),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Text(
            text = "$${producto.precio}",
            color = Color(0xFF1E90FF),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text(
            text = producto.descripcion,
            color = Color.White,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        Text(
            text = "Categoría: ${producto.categoria}",
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}
