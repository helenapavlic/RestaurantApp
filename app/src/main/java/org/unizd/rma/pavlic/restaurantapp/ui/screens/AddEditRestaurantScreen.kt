package org.unizd.rma.pavlic.restaurantapp.ui.screens

import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import android.widget.DatePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import org.unizd.rma.pavlic.restaurantapp.model.Restaurant
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

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

    // Launcher za galeriju
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri?.toString()
    }

    // Launcher za kameru
    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            imageUri = tempCameraUri.toString()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Naziv") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

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

        // Datum otvaranja
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

        // Slika
        imageUri?.let { uri ->
            Spacer(Modifier.height(8.dp))
            AsyncImage(
                model = uri,
                contentDescription = "Slika restorana",
                modifier = Modifier.size(150.dp),
                contentScale = ContentScale.Crop
            )
        }

        Column {
            Text("Dodaj sliku:")
            Spacer(Modifier.height(8.dp))
        }
        Row {
            Button(onClick = { galleryLauncher.launch("image/*") }) { Text("Odaberi iz galerije") }
        }
        Spacer(Modifier.height(8.dp))
        Row{
            Button(onClick = {
                val uri = createImageUri(context)
                tempCameraUri = uri
                cameraLauncher.launch(uri)
            }) { Text("Kamera") }
        }

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

fun createImageUri(context: Context): Uri {
    val file = File(context.cacheDir, "${System.currentTimeMillis()}.jpg")
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        file
    )
}
