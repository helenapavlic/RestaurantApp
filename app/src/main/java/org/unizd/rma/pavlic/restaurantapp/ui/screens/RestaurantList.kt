package org.unizd.rma.pavlic.restaurantapp.ui.screens

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.unizd.rma.pavlic.restaurantapp.model.Restaurant
@Composable
fun RestaurantList(
    restaurants: List<Restaurant>,
    onDelete: (Restaurant) -> Unit,
    onEdit: (Restaurant) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(restaurants) { r ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { onEdit(r) }
            ) {
                Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    if (r.imageUri != null) {
                        AsyncImage(
                            model = r.imageUri,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = androidx.compose.ui.res.painterResource(id = android.R.drawable.ic_menu_report_image),
                            contentDescription = null,
                            modifier = Modifier.size(64.dp)
                        )
                    }

                    Spacer(Modifier.width(8.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(r.name, style = MaterialTheme.typography.headlineSmall)
                        Text(r.location, style = MaterialTheme.typography.bodyMedium)
                        Text(r.cuisine, style = MaterialTheme.typography.bodySmall)
                    }

                    IconButton(onClick = { onDelete(r) }) {
                        Icon(
                            painter = androidx.compose.ui.res.painterResource(id = android.R.drawable.ic_menu_delete),
                            contentDescription = "Obriši"
                        )
                    }
                }
            }
        }
    }
}
