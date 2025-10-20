package com.example.levelup_gamerapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter

data class Producto(
    val nombre: String,
    val precio: String,
    val imagenUrl: String
)

data class Categoria(
    val nombre: String,
    val iconUrl: String
)

@Composable
fun PantallaPrincipal(navController: NavHostController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        item { BannerPrincipal() }
        item { ProductosDestacados() }
        item { CategoriasSeccion() }
        item { FooterSeccion() }
    }
}

@Composable
fun BannerPrincipal() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                "https://www.azernews.az/media/2023/11/27/2023_rog_zephyrus_duo_16_gx650_scenario_photo_01.jpg?v=1701092248"
            ),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .background(Color(0xAA000000))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("PRODUCTOS DESTACADOS", color = Color(0xFF39FF14), fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text("Lo más vendido esta semana", color = Color(0xFF1E90FF), fontSize = 16.sp)
        }
    }
}

@Composable
fun ProductosDestacados() {
    val productos = listOf(
        Producto("Teclado Mecánico RGB", "$89.990", "https://media.falabella.com/falabellaCL/17143546_2/w=1500,h=1500,fit=pad"),
        Producto("Audífonos Gamer HyperX", "$79.990", "https://www.powerplanetonline.com/cdnassets/hyperx_cloud_stinger_core_wireless_7.1_ps4_02_l.jpg"),
        Producto("Mouse Logitech G Pro", "$59.990", "https://i.blogs.es/77d3cc/logitechgpro/1366_2000.jpg"),
        Producto("Silla Gamer Razer", "$189.990", "https://cdn.mos.cms.futurecdn.net/epdKe7LXYbD7nAJ9KUQ6t8.jpg")
    )

    Text("Destacados", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(16.dp))

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .padding(horizontal = 8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(productos) { producto ->
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF111111))
            ) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(producto.imagenUrl),
                        contentDescription = producto.nombre,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(producto.nombre, color = Color(0xFF39FF14), fontWeight = FontWeight.Bold)
                    Text(producto.precio, color = Color(0xFF1E90FF), fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
fun CategoriasSeccion() {
    val categorias = listOf(
        Categoria("Consolas", "https://img.icons8.com/?size=100&id=Rz3NTZkvlexz&format=png&color=39ff14"),
        Categoria("Accesorios", "https://img.icons8.com/?size=100&id=MlGniXnp6gP1&format=png&color=39ff14"),
        Categoria("Juegos", "https://img.icons8.com/?size=100&id=Tb5XGbRvSX2v&format=png&color=39ff14"),
        Categoria("Componentes", "https://img.icons8.com/?size=100&id=cnYTrlcPnC0e&format=png&color=39ff14")
    )

    Text("Explora por Categorías", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E90FF), modifier = Modifier.padding(16.dp))

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(horizontal = 8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(categorias) { categoria ->
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF000000))
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(categoria.iconUrl),
                        contentDescription = categoria.nombre,
                        modifier = Modifier.size(70.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(categoria.nombre, color = Color(0xFF39FF14), fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun FooterSeccion() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "© 2025 LEVEL-UP GAMER. Todos los derechos reservados.",
            color = Color(0xFF1E90FF),
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}
