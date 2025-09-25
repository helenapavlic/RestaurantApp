package org.unizd.rma.pavlic.restaurantapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import org.unizd.rma.pavlic.restaurantapp.ui.screens.MainScreen
import org.unizd.rma.pavlic.restaurantapp.ui.RestaurantViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val vm: RestaurantViewModel = viewModel()
            MainScreen(vm = vm)
        }
    }
}
