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
import com.example.practico3.data.entities.Tarea
import com.example.practico3.viewmodel.TareaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarScreen(
    tareaId: Int,
    viewModel: TareaViewModel,
    navController: NavController
) {
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fechaVencimiento by remember { mutableStateOf("") }
    var completada by remember { mutableStateOf(false) }

    // Estados para el Dropdown de prioridad
    var expanded by remember { mutableStateOf(false) }
    val prioridades = listOf("Alta", "Media", "Baja")
    var prioridadSeleccionada by remember { mutableStateOf("Media") }

    // Guardamos una referencia de la tarea original para no perder su ID ni su fecha de creación
    var tareaOriginal by remember { mutableStateOf<Tarea?>(null) }

    // LaunchedEffect carga los datos existentes de la tarea por defecto al entrar a la pantalla
    LaunchedEffect(tareaId) {
        viewModel.getTareaById(tareaId) { tarea ->
            tareaOriginal = tarea
            titulo = tarea.title
            descripcion = tarea.description
            prioridadSeleccionada = tarea.priority
            completada = tarea.completed
            fechaVencimiento = tarea.dueDate // Recuerda tener 'dueDate' añadido en tu data class Tarea
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Editar Tarea",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo Título
        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo Descripción
        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo Fecha de vencimiento
        OutlinedTextField(
            value = fechaVencimiento,
            onValueChange = { fechaVencimiento = it },
            label = { Text("Fecha de vencimiento") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown de Prioridad
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


        // Botón Guardar Cambios
        Button(
            onClick = {
                if (titulo.isNotBlank() && tareaOriginal != null) {
                    // Modificamos el objeto original manteniendo sus datos intactos de BD
                    val tareaModificada = tareaOriginal!!.copy(
                        title = titulo,
                        description = descripcion,
                        priority = prioridadSeleccionada,
                        completed = completada,
                        dueDate = fechaVencimiento
                    )

                    viewModel.update(tareaModificada)
                    navController.popBackStack() // Regresa a la lista
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = titulo.isNotBlank()
        ) {
            Text("Guardar Cambios")
        }
    }
}