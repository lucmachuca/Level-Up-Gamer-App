@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.levelup_gamerapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

data class Noticia(
    val id: Int,
    val titulo: String,
    val resumen: String,
    val imagenUrl: String
)

@Composable
fun PantallaNovedades(navBack: () -> Unit = {}) {
    val novedades = remember {
        listOf(
            Noticia(
                id = 1,
                titulo = "Nueva consola XYZ ya disponible",
                resumen = "La consola XYZ llega con rendimiento de última generación, diseño compacto y soporte para 8K.",
                imagenUrl = "https://via.placeholder.com/600x300.png?text=Consola+XYZ"
            ),
            Noticia(
                id = 2,
                titulo = "Tendencias de accesorios gamer 2025",
                resumen = "Descubre los accesorios más demandados este año: teclados compactos, sillas ergonómicas y auriculares inalámbricos.",
                imagenUrl = "https://via.placeholder.com/600x300.png?text=Accesorios+Gamer"
            ),
            Noticia(
                id = 3,
                titulo = "Ofertas imperdibles en Semana Gaming",
                resumen = "Aprovecha descuentos de hasta 50% en componentes y periféricos seleccionados.",
                imagenUrl = "https://via.placeholder.com/600x300.png?text=Ofertas+Semana+Gaming"
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Novedades",
                        color = Color(0xFF39FF14),
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        },
        containerColor = Color.Black
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(padding)
        ) {
            items(novedades) { noticia ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .clickable {
                            // Aquí podrás navegar al detalle: nav.navigate("noticia/${noticia.id}")
                        },
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF111111))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Image(
                            painter = rememberAsyncImagePainter(noticia.imagenUrl),
                            contentDescription = noticia.titulo,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            noticia.titulo,
                            color = Color(0xFF39FF14),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            noticia.resumen,
                            color = Color.White,
                            fontSize = 14.sp,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Divider(color = Color(0xFF1E90FF), thickness = 1.dp)
                    }
                }
            }
        }
    }
}
