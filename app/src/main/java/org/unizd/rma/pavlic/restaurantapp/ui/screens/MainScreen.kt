package org.unizd.rma.pavlic.restaurantapp.ui.screens
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.unizd.rma.pavlic.restaurantapp.ui.RestaurantViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(vm: RestaurantViewModel) {
    val navController = rememberNavController()
    val restaurants = vm.restaurants.collectAsState().value

    NavHost(
        navController = navController,
        startDestination = Screen.List.route,
        modifier = Modifier.fillMaxSize()
    ) {

        // --- LIST SCREEN ---
        composable(Screen.List.route) {
            Scaffold(
                topBar = { TopAppBar(title = { Text("Restorani") }) },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { navController.navigate(Screen.AddEdit.createRoute(null)) }
                    ) { Text("+") }
                }
            ) { padding ->
                RestaurantList(
                    restaurants = restaurants,
                    onDelete = { vm.deleteRestaurant(it) },
                    onEdit = { r -> navController.navigate(Screen.AddEdit.createRoute(r.id)) },
                    modifier = Modifier.padding(padding)
                )
            }
        }

        // --- ADD / EDIT SCREEN ---
        composable(
            Screen.AddEdit.route,
            arguments = listOf(navArgument("restaurantId") { type = NavType.LongType })
        ) { backStackEntry ->
            val restaurantId = backStackEntry.arguments?.getLong("restaurantId") ?: 0L
            val restaurant = restaurants.find { it.id == restaurantId }

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(if (restaurant != null) "Uredi restoran" else "Dodaj restoran") },
                        navigationIcon = {
                            IconButton(onClick = { navController.navigateUp() }) {
                                Icon(Icons.Filled.ArrowBack, contentDescription = "Nazad")
                            }
                        }
                    )
                }
            ) { padding ->
                AddEditRestaurantScreen(
                    restaurant = restaurant,
                    onSave = {
                        if (restaurant != null) vm.updateRestaurant(it)
                        else vm.addRestaurant(it)
                        navController.popBackStack()
                    },
                    onCancel = { navController.popBackStack() },
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}
