package com.example.levelup_gamerapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.levelup_gamerapp.local.AppDatabase
import com.example.levelup_gamerapp.local.ProductosEntity
import com.example.levelup_gamerapp.repository.ProductosRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaProducto(id: Int, onNavigateBack: () -> Unit) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val dao = AppDatabase.obtenerBaseDatos(context).productosDao()
    val repo = ProductosRepository(dao)

    var producto by remember { mutableStateOf<ProductosEntity?>(null) }

    // Carga segura del producto
    LaunchedEffect(id) {
        withContext(Dispatchers.IO) {
            producto = repo.obtenerProductos().find { it.id == id }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del producto", color = Color(0xFF39FF14)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color(0xFF39FF14)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        },
        containerColor = Color.Black
    ) { padding ->
        producto?.let { prod ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(padding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Imagen protegida
                val painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(context)
                        .data(prod.imagenUrl)
                        .crossfade(true)
                        .error(android.R.drawable.ic_menu_report_image)
                        .placeholder(android.R.drawable.ic_menu_gallery)
                        .build()
                )

                Image(
                    painter = painter,
                    contentDescription = prod.nombre,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = prod.nombre,
                    color = Color(0xFF39FF14),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$${String.format("%.0f", prod.precio)}",
                    color = Color(0xFF1E90FF),
                    fontSize = 18.sp,
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = prod.descripcion,
                    color = Color.White,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Categoría: ${prod.categoria}",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        } ?: Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color(0xFF39FF14))
        }
    }
}
