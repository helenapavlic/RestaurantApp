package org.unizd.rma.pavlic.restaurantapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.unizd.rma.pavlic.restaurantapp.db.AppDatabase
import org.unizd.rma.pavlic.restaurantapp.model.Restaurant
import org.unizd.rma.pavlic.restaurantapp.repo.RestaurantRepository


class RestaurantViewModel(application: Application): AndroidViewModel(application) {
    private val repo: RestaurantRepository
    val restaurants: StateFlow<List<Restaurant>>

    init {
        val db = AppDatabase.getInstance(application)
        repo = RestaurantRepository(db.restaurantDao())
        restaurants = repo.getAll().map { it }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }

    fun addRestaurant(r: Restaurant) = viewModelScope.launch { repo.insert(r) }
    fun updateRestaurant(r: Restaurant) = viewModelScope.launch { repo.update(r) }
    fun deleteRestaurant(r: Restaurant) = viewModelScope.launch { repo.delete(r) }
}
