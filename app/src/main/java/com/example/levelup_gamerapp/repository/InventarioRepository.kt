package com.example.levelup_gamerapp.repository

import com.example.levelup_gamerapp.local.InventarioProductoDao
import com.example.levelup_gamerapp.local.InventarioProductoEntity

class InventarioRepository(private val dao: InventarioProductoDao) {

    suspend fun insertar(inventario: InventarioProductoEntity) = dao.insertarInventario(inventario)
    suspend fun obtener() = dao.obtenerInventario()
    suspend fun actualizar(inventario: InventarioProductoEntity) = dao.actualizarInventario(inventario)
    suspend fun eliminar(inventario: InventarioProductoEntity) = dao.eliminarInventario(inventario)
}
