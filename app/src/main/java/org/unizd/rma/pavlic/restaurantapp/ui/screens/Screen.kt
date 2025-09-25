package org.unizd.rma.pavlic.restaurantapp.ui.screens

sealed class Screen(val route: String) {
    object List : Screen("restaurant_list")
    object AddEdit : Screen("restaurant_add_edit/{restaurantId}") {
        fun createRoute(id: Long?) = "restaurant_add_edit/${id ?: 0L}"
    }
}

