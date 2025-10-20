package com.example.levelup_gamerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.levelup_gamerapp.repository.InventarioRepository

class InventarioProductoViewModelFactory(private val repo: InventarioRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventarioProductosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InventarioProductosViewModel(repo) as T
        }
        throw IllegalArgumentException("ViewModel desconocido")
    }
}
