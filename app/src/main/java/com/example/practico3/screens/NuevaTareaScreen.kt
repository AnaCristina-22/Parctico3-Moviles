package com.example.practico3.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.practico3.data.entities.Tag
import com.example.practico3.data.entities.Tarea
import com.example.practico3.viewmodel.TareaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevaTareaScreen(
    viewModel: TareaViewModel,
    navController: NavController
) {
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fechaVencimiento by remember { mutableStateOf("") }

    // El estado por defecto se crea sin marcar (false = Pendiente)
    var completada by remember { mutableStateOf(false) }

    // Estados para el Dropdown de prioridad
    var expanded by remember { mutableStateOf(false) }
    val prioridades = listOf("Alta", "Media", "Baja")
    var prioridadSeleccionada by remember { mutableStateOf(prioridades[1]) } // "Media" por defecto

    // Estados para las etiquetas (Tags) existentes en la base de datos
    val tags by viewModel.tags.collectAsState(initial = emptyList())
    var selectedTags by remember { mutableStateOf(setOf<Tag>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Nueva Tarea",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo: Título (Obligatorio)
        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo: Descripción (Opcional)
        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo: Fecha de vencimiento (Opcional)
        OutlinedTextField(
            value = fechaVencimiento,
            onValueChange = { fechaVencimiento = it },
            label = { Text("Fecha de vencimiento (ej: 18/05/2026)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Selector de Prioridad (Dropdown Menu)
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = prioridadSeleccionada,
                onValueChange = {},
                readOnly = true,
                label = { Text("Prioridad") },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                prioridades.forEach { prioridad ->
                    DropdownMenuItem(
                        text = { Text(prioridad) },
                        onClick = {
                            prioridadSeleccionada = prioridad
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Gestión de Estado (Default: Sin marcar / Pendiente)
        Text(
            text = "Estado inicial:",
            style = MaterialTheme.typography.titleMedium
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = completada,
                onCheckedChange = { completada = it }
            )
            Text(text = if (completada) "Completada" else "Pendiente (Sin marcar)")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Selección Múltiple de Etiquetas (Tags)
        Text(
            text = "Asociar Etiquetas:",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (tags.isEmpty()) {
            Text(
                text = "No hay etiquetas creadas aún. Puedes agregarlas desde la pantalla de Etiquetas.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            tags.forEach { tag ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp)
                ) {
                    Checkbox(
                        checked = selectedTags.contains(tag),
                        onCheckedChange = { isChecked ->
                            selectedTags = if (isChecked) {
                                selectedTags + tag
                            } else {
                                selectedTags - tag
                            }
                        }
                    )
                    Text(
                        text = tag.name,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Botón Guardar con validaciones de negocio
        Button(
            onClick = {
                if (titulo.isNotBlank()) {
                    // El campo 'createdAt' se genera automáticamente con System.currentTimeMillis() dentro de la entidad
                    val nuevaTarea = Tarea(
                        title = titulo,
                        description = descripcion,
                        priority = prioridadSeleccionada,
                        completed = completada,
                        dueDate = fechaVencimiento
                    )


                    viewModel.crearTareaConTags(nuevaTarea, selectedTags.toList())


                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = titulo.isNotBlank() // Deshabilita el botón si el título obligatorio está vacío
        ) {
            Text("Guardar")
        }
    }
}