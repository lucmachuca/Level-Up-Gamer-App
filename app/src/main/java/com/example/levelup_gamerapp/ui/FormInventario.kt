package com.example.levelup_gamerapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FormInventario(
    onDismiss: () -> Unit,
    onConfirm: (Int, Int, String) -> Unit
) {
    var productoId by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                onConfirm(
                    productoId.toIntOrNull() ?: 0,
                    cantidad.toIntOrNull() ?: 0,
                    estado
                )
            }) { Text("Guardar") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } },
        title = { Text("Agregar al inventario") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = productoId, onValueChange = { productoId = it }, label = { Text("ID Producto") })
                OutlinedTextField(value = cantidad, onValueChange = { cantidad = it }, label = { Text("Cantidad") })
                OutlinedTextField(value = estado, onValueChange = { estado = it }, label = { Text("Estado") })
            }
        }
    )
}
