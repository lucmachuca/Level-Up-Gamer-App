package com.example.levelup_gamerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamerapp.local.ProductosEntity
import com.example.levelup_gamerapp.repository.ProductosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductosViewModel(private val repository: ProductosRepository) : ViewModel() {

    private val _productos = MutableStateFlow<List<ProductosEntity>>(emptyList())
    val productos: StateFlow<List<ProductosEntity>> = _productos

    init {
        cargarProductos()
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

    fun insertarEjemploSiVacio() {
        viewModelScope.launch {
            val actuales = repository.obtenerProductos()
            if (actuales.isEmpty()) {
                repository.eliminarTodos()
                val ejemplos = listOf(
                    ProductosEntity(
                        nombre = "Teclado Mecánico RGB",
                        descripcion = "Teclado gamer con luces RGB y switches azules.",
                        precio = 89.990,
                        imagenUrl = "https://media.falabella.com/falabellaCL/17143546_2/w=1500,h=1500,fit=pad",
                        categoria = "Accesorios"
                    ),
                    ProductosEntity(
                        nombre = "Mouse Logitech G Pro",
                        descripcion = "Sensor HERO 25K, diseño ligero y precisión extrema.",
                        precio = 59.990,
                        imagenUrl = "https://i.blogs.es/77d3cc/logitechgpro/1366_2000.jpg",
                        categoria = "Accesorios"
                    ),
                    ProductosEntity(
                        nombre = "Silla Razer Iskur",
                        descripcion = "Silla ergonómica gamer con soporte lumbar ajustable.",
                        precio = 189.990,
                        imagenUrl = "https://cdn.mos.cms.futurecdn.net/epdKe7LXYbD7nAJ9KUQ6t8.jpg",
                        categoria = "Sillas"
                    ),
                    ProductosEntity(
                        nombre = "Auriculares Corsair Void",
                        descripcion = "Sonido envolvente 7.1 con micrófono retráctil.",
                        precio = 99.990,
                        imagenUrl = "https://m.media-amazon.com/images/I/71OxxzdxmFL._AC_SL1500_.jpg",
                        categoria = "Audio"
                    )
                )
                ejemplos.forEach { repository.insertarProducto(it) }
                cargarProductos()
            }
        }
    }

}
