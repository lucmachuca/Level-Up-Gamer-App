package com.example.levelup_gamerapp.repository

import com.example.levelup_gamerapp.local.ProductosDao
import com.example.levelup_gamerapp.local.ProductosEntity

class ProductosRepository(private val dao: ProductosDao) {

    suspend fun obtenerProductos(): List<ProductosEntity> {
        return dao.obtenerTodos()
    }

    suspend fun insertarProducto(producto: ProductosEntity) {
        dao.insertarProducto(producto)
    }

    suspend fun eliminarProducto(producto: ProductosEntity) {
        dao.eliminarProducto(producto)
    }

    suspend fun eliminarTodos() {
        dao.eliminarTodos()
    }

 //metodo para productos por id
    suspend fun obtenerProductoPorId(id: Int): ProductosEntity? {
        return dao.obtenerProductoPorId(id)
    }
}
