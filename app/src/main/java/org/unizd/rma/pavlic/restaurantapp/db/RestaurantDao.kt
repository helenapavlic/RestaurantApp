package org.unizd.rma.pavlic.restaurantapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.unizd.rma.pavlic.restaurantapp.model.Restaurant


@Dao
interface RestaurantDao {
    @Query("SELECT * FROM restaurants ORDER BY openingDate DESC")
    fun getAll(): Flow<List<Restaurant>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(restaurant: Restaurant): Long

    @Update
    suspend fun update(restaurant: Restaurant)

    @Delete
    suspend fun delete(restaurant: Restaurant)
}
