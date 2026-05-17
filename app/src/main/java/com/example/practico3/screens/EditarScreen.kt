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
fun EditarScreen(
    tareaId: Int,
    viewModel: TareaViewModel,
    navController: NavController
) {
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fechaVencimiento by remember { mutableStateOf("") }
    var completada by remember { mutableStateOf(false) }

    var expanded by remember { mutableStateOf(false) }
    val prioridades = listOf("Alta", "Media", "Baja")
    var prioridadSeleccionada by remember { mutableStateOf("Media") }

    var tareaOriginal by remember { mutableStateOf<Tarea?>(null) }

    // Listas reactivas para gestionar el cambio de etiquetas
    val todosLosTags by viewModel.tags.collectAsState(initial = emptyList())
    var tagsSeleccionados by remember { mutableStateOf(setOf<Tag>()) }

    LaunchedEffect(tareaId) {
        viewModel.getTareaConTagsById(tareaId) { item ->
            tareaOriginal = item.tarea
            titulo = item.tarea.title
            descripcion = item.tarea.description
            prioridadSeleccionada = item.tarea.priority
            completada = item.tarea.completed
            fechaVencimiento = item.tarea.dueDate
            tagsSeleccionados = item.tags.toSet() // Precarga los tags que ya tenía asignados
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Editar Tarea", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = titulo, onValueChange = { titulo = it }, label = { Text("Título") }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(value = descripcion, onValueChange = { descripcion = it }, label = { Text("Descripción") }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(value = fechaVencimiento, onValueChange = { fechaVencimiento = it }, label = { Text("Fecha de vencimiento") }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(16.dp))

        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            OutlinedTextField(
                value = prioridadSeleccionada,
                onValueChange = {},
                readOnly = true,
                label = { Text("Prioridad") },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                prioridades.forEach { p -> DropdownMenuItem(text = { Text(p) }, onClick = { prioridadSeleccionada = p; expanded = false }) }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Checkbox(checked = completada, onCheckedChange = { completada = it })
            Text(if (completada) "Completada" else "Pendiente")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // REQUERIMIENTO 2: GESTIÓN REACTIVA DE ETIQUETAS EN LA EDICIÓN (Asociar/Desasociar)
        Text("Modificar Etiquetas:", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        todosLosTags.forEach { tag ->
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Checkbox(
                    checked = tagsSeleccionados.contains(tag),
                    onCheckedChange = { checked ->
                        tagsSeleccionados = if (checked) tagsSeleccionados + tag else tagsSeleccionados - tag
                    }
                )
                Text(tag.name, modifier = Modifier.padding(start = 8.dp))
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (titulo.isNotBlank() && tareaOriginal != null) {
                    val tareaModificada = tareaOriginal!!.copy(
                        title = titulo,
                        description = descripcion,
                        priority = prioridadSeleccionada,
                        completed = completada,
                        dueDate = fechaVencimiento
                    )
                    // Guarda los cambios de texto y regenera las llaves en la tabla intermedia
                    viewModel.actualizarTareaConTags(tareaModificada, tagsSeleccionados.toList())
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = titulo.isNotBlank()
        ) {
            Text("Guardar Cambios")
        }
    }
}