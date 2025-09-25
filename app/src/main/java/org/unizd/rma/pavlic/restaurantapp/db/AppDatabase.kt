package org.unizd.rma.pavlic.restaurantapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.unizd.rma.pavlic.restaurantapp.model.Restaurant


@Database(entities = [Restaurant::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "restaurant_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
