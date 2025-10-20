package com.example.levelup_gamerapp.ui

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Size
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.levelup_gamerapp.viewmodel.RegistroUsuarioViewModel

// ✅ Reutiliza el CampoTexto del LoginScreen
import com.example.levelup_gamerapp.ui.CampoTexto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(
    vm: RegistroUsuarioViewModel,
    onSaved: () -> Unit = {}
) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }

    var foto by remember { mutableStateOf<Bitmap?>(null) }

    val mensaje by vm.mensaje.collectAsState()
    val context = LocalContext.current

    // ---- Cámara y galería ----
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap -> bitmap?.let { foto = it } }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val thumb = context.contentResolver.loadThumbnail(it, Size(200, 200), null)
            foto = thumb
        }
    }

    val requestGalleryPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) galleryLauncher.launch("image/*")
    }

    fun abrirGaleriaConPermiso() {
        val permiso =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                Manifest.permission.READ_MEDIA_IMAGES
            else
                Manifest.permission.READ_EXTERNAL_STORAGE

        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(context, permiso) -> galleryLauncher.launch("image/*")
            else -> requestGalleryPermission.launch(permiso)
        }
    }

    // ---- UI ----
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                "Registro de Usuario",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .size(160.dp)
                    .clip(CircleShape)
                    .border(BorderStroke(2.dp, MaterialTheme.colorScheme.secondary))
                    .background(Color.DarkGray.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                if (foto != null) {
                    Image(
                        bitmap = foto!!.asImageBitmap(),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Text("Sin foto", color = Color.Gray)
                }
            }

            Spacer(Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { cameraLauncher.launch() },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) { Text("Tomar foto", color = Color.Black) }

                Button(
                    onClick = { abrirGaleriaConPermiso() },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) { Text("Subir desde galería", color = Color.Black) }
            }

            Spacer(Modifier.height(20.dp))

            // Campos
            CampoTexto("Nombre", nombre) { nombre = it }
            CampoTexto("Apellido", apellido) { apellido = it }
            CampoTexto("Correo", correo) { correo = it }
            CampoTexto("Contraseña", contrasena, true) { contrasena = it }
            CampoTexto("Edad", edad) { edad = it }

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {
                    val edadInt = edad.toIntOrNull() ?: 0
                    vm.registrar(nombre, apellido, correo, contrasena, edadInt, foto)
                    onSaved()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) { Text("Registrarse") }

            if (mensaje.isNotEmpty()) {
                Spacer(Modifier.height(12.dp))
                Text(
                    text = mensaje,
                    color = when {
                        mensaje.contains("exitoso", true) -> MaterialTheme.colorScheme.secondary
                        mensaje.contains("descuento", true) -> MaterialTheme.colorScheme.primary
                        else -> MaterialTheme.colorScheme.error
                    }
                )
            }
        }
    }
}
