package com.example.levelup_gamerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamerapp.local.ProductosEntity
import com.example.levelup_gamerapp.repository.ProductosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class ProductosViewModel(private val repository: ProductosRepository) : ViewModel() {

    private val _productos = MutableStateFlow<List<ProductosEntity>>(emptyList())
    val productos: StateFlow<List<ProductosEntity>> = _productos

    init {
        // 🔹 Cargar productos o insertar ejemplos si la base está vacía
        viewModelScope.launch {
            insertarEjemploSiVacio()
            cargarProductos()
        }
    }

    fun cargarProductos() {
        viewModelScope.launch {
            _productos.value = repository.obtenerProductos()
        }
    }

    fun agregarProducto(producto: ProductosEntity) {
        viewModelScope.launch {
            repository.insertarProducto(producto)
            cargarProductos()
        }
    }

    fun eliminarProducto(producto: ProductosEntity) {
        viewModelScope.launch {
            repository.eliminarProducto(producto)
            cargarProductos()
        }
    }

    fun eliminarTodos() {
        viewModelScope.launch {
            repository.eliminarTodos()
            cargarProductos()
        }
    }

    // 🆕 Permite obtener un producto específico por ID (para PantallaProducto)
    fun obtenerProductoPorId(id: Int) = flow {
        val producto = repository.obtenerProductoPorId(id)
        emit(producto)
    }

    private suspend fun insertarEjemploSiVacio() {
        val actuales = repository.obtenerProductos()
        if (actuales.isEmpty()) {
            repository.eliminarTodos()
            val ejemplos = listOf(
                ProductosEntity(
                    nombre = "Teclado Mecánico RGB",
                    descripcion = "Teclado gamer con luces RGB y switches azules.",
                    precio = 89990.0,
                    imagenUrl = "https://media.falabella.com/falabellaCL/17143546_2/w=1500,h=1500,fit=pad",
                    categoria = "Teclados",
                    cantidadDisponible = 20
                ),
                ProductosEntity(
                    nombre = "Mouse Logitech G Pro Wireless",
                    descripcion = "Sensor HERO 25K, diseño ligero y precisión extrema.",
                    precio = 59990.0,
                    imagenUrl = "https://media.falabella.com/falabellaCL/137291578_02/w=1500,h=1500,fit=pad",
                    categoria = "Mouse",
                    cantidadDisponible = 15
                ),
                ProductosEntity(
                    nombre = "Silla Razer Iskur",
                    descripcion = "Silla ergonómica gamer con soporte lumbar ajustable.",
                    precio = 189990.0,
                    imagenUrl = "https://media.falabella.com/falabellaCL/140930116_01/w=1500,h=1500,fit=pad",
                    categoria = "Sillas",
                    cantidadDisponible = 10
                ),
                ProductosEntity(
                    nombre = "Auriculares Corsair Void",
                    descripcion = "Sonido envolvente 7.1 con micrófono retráctil.",
                    precio = 99990.0,
                    imagenUrl = "https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcTzCJ7K0bbX53NcXnCve6gnFDW-lu97zSUB5O4xVUfPShWtOu-tFepgEaKuJ_g4LAsr8zSPpkTkj92Dxgi6rvPoO9czMhZvJS_h_68PztkbkCQPXaW-02sO",
                    categoria = "Audífonos",
                    cantidadDisponible = 25
                )
            )
            ejemplos.forEach { repository.insertarProducto(it) }
        }
    }
}
