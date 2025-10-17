package com.example.levelup_gamerapp.repository

import com.example.levelup_gamerapp.local.ProductosDao
import com.example.levelup_gamerapp.local.ProductosEntity

class ProductosRepository(private val productosDao: ProductosDao) {

    suspend fun obtenerProductos(): List<ProductosEntity> {
        return productosDao.obtenerTodos()
    }

    suspend fun insertarProducto(producto: ProductosEntity) {
        productosDao.insertarProducto(producto)
    }

    suspend fun eliminarProducto(producto: ProductosEntity) {
        productosDao.eliminarProducto(producto)
    }

    suspend fun eliminarTodos() {
        productosDao.eliminarTodos()
    }
}
