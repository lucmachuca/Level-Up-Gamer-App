package com.example.levelup_gamerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamerapp.local.InventarioProductoEntity
import com.example.levelup_gamerapp.repository.InventarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class InventarioProductosViewModel(private val repository: InventarioRepository) : ViewModel() {

    private val _inventario = MutableStateFlow<List<InventarioProductoEntity>>(emptyList())
    val inventario: StateFlow<List<InventarioProductoEntity>> = _inventario

    private val _mensaje = MutableStateFlow("")
    val mensaje: StateFlow<String> = _mensaje

    fun cargarInventario() {
        viewModelScope.launch {
            try {
                _inventario.value = repository.obtener()
            } catch (e: Exception) {
                _mensaje.value = "Error cargando inventario: ${e.message}"
            }
        }
    }

    fun agregarRegistro(productoId: Int, cantidad: Int, estado: String) {
        viewModelScope.launch {
            val registro = InventarioProductoEntity(productoId = productoId, cantidad = cantidad, estado = estado)
            repository.insertar(registro)
            cargarInventario()
            _mensaje.value = "Registro agregado"
        }
    }

    fun eliminarRegistro(item: InventarioProductoEntity) {
        viewModelScope.launch {
            repository.eliminar(item)
            cargarInventario()
            _mensaje.value = "Registro eliminado"
        }
    }
}
