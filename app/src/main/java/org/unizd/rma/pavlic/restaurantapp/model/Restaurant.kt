package org.unizd.rma.pavlic.restaurantapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "restaurants")
data class Restaurant(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val location: String,
    val cuisine: String,
    val openingDate: Long, // epoch millis
    val imageUri: String? // content uri as string
)
