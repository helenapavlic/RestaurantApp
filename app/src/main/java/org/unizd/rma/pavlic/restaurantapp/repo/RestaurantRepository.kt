package org.unizd.rma.pavlic.restaurantapp.repo

import kotlinx.coroutines.flow.Flow
import org.unizd.rma.pavlic.restaurantapp.db.RestaurantDao
import org.unizd.rma.pavlic.restaurantapp.model.Restaurant


class RestaurantRepository(private val dao: RestaurantDao) {
    fun getAll(): Flow<List<Restaurant>> = dao.getAll()
    suspend fun insert(r: Restaurant) = dao.insert(r)
    suspend fun update(r: Restaurant) = dao.update(r)
    suspend fun delete(r: Restaurant) = dao.delete(r)
}
