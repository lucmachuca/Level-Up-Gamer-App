package com.example.levelup_gamerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamerapp.local.CarritoEntity
import com.example.levelup_gamerapp.repository.CarritoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CarritoViewModel(private val repo: CarritoRepository) : ViewModel() {

    val carrito = repo.carrito.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun agregar(item: CarritoEntity) {
        viewModelScope.launch { repo.agregar(item) }
    }

    fun eliminar(item: CarritoEntity) {
        viewModelScope.launch { repo.eliminar(item) }
    }

    fun vaciar() {
        viewModelScope.launch { repo.vaciar() }
    }
}
