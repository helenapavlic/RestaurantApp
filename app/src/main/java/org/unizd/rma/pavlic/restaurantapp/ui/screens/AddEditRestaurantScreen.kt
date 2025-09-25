package org.unizd.rma.pavlic.restaurantapp.ui.screens

import android.app.DatePickerDialog
import android.net.Uri
import android.widget.DatePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.unizd.rma.pavlic.restaurantapp.model.Restaurant
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditRestaurantScreen(
    restaurant: Restaurant? = null,
    onSave: (Restaurant) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf(restaurant?.name ?: "") }
    var location by remember { mutableStateOf(restaurant?.location ?: "") }
    var cuisine by remember { mutableStateOf(restaurant?.cuisine ?: "Talijanska") }
    var openingDate by remember { mutableStateOf(restaurant?.openingDate ?: Calendar.getInstance().timeInMillis) }
    var imageUri by remember { mutableStateOf(restaurant?.imageUri) }

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri?.toString()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Naziv restorana - cijela širina
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Naziv") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        // Lokacija - cijela širina
        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Lokacija") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        // Dropdown za tip kuhinje
        val cuisines = listOf("Talijanska", "Meksička", "Američka", "Azijska", "Mediteranska")
        var expanded by remember { mutableStateOf(false) }

        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            OutlinedTextField(
                value = cuisine,
                onValueChange = {},
                readOnly = true,
                label = { Text("Tip kuhinje") },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                cuisines.forEach { c ->
                    DropdownMenuItem(text = { Text(c) }, onClick = { cuisine = c; expanded = false })
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        // Datum otvaranja - datum u jednom retku, gumb ispod
        Column(modifier = Modifier.fillMaxWidth()) {
            Text("Datum otvaranja: ")
            Spacer(Modifier.height(4.dp))
            Text(
                SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date(openingDate)),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(4.dp))
            Button(onClick = {
                val cal = Calendar.getInstance().apply { timeInMillis = openingDate }
                DatePickerDialog(
                    context,
                    { _: DatePicker, y, m, d ->
                        val newCal = Calendar.getInstance()
                        newCal.set(y, m, d)
                        openingDate = newCal.timeInMillis
                    },
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }) {
                Text("Odaberi datum")
            }
        }


        Spacer(Modifier.height(8.dp))

        // Odabir slike

        imageUri?.let { uri ->
            Spacer(Modifier.height(8.dp))
            AsyncImage(
                model = uri,
                contentDescription = "Slika restorana",
                modifier = Modifier.size(150.dp),
                contentScale = ContentScale.Crop
            )
        }
        Button(onClick = { launcher.launch("image/*") }) { Text("Odaberi sliku") }

        Spacer(Modifier.height(16.dp))
        Row {
            Button(
                onClick = {
                    val r = Restaurant(
                        id = restaurant?.id ?: 0L,
                        name = name,
                        location = location,
                        cuisine = cuisine,
                        openingDate = openingDate,
                        imageUri = imageUri
                    )
                    onSave(r)
                },
                enabled = name.isNotBlank()
            ) { Text("Spremi") }

            Spacer(Modifier.width(8.dp))
            OutlinedButton(onClick = onCancel) { Text("Otkaži") }
        }
    }
}
