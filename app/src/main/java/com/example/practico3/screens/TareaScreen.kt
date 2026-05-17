package com.example.practico3.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.practico3.viewmodel.TareaState
import com.example.practico3.viewmodel.TareaViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TareaScreen(
    viewModel: TareaViewModel,
    navController: NavController
) {
    val verdeAgua = Color(0xFFA8E6CF)
    val verdeSuave = Color(0xFFDCEDC1)
    val amarilloPastel = Color(0xFFFFF3B0)
    val crema = Color(0xFFFFFBF2)
    val verdeOscuro = Color(0xFF4E7C59)

    var busqueda by remember { mutableStateOf("") }
    val state by viewModel.state.collectAsState()

    // Estados de menús desplegables
    var menuOrdenExpanded by remember { mutableStateOf(false) }
    var menuEstadoExpanded by remember { mutableStateOf(false) }
    var menuPrioridadExpanded by remember { mutableStateOf(false) }
    var menuEtiquetaExpanded by remember { mutableStateOf(false) }

    // Criterios de Selección
    var ordenSeleccionado by remember { mutableStateOf("Fecha de Creación") }
    var estadoSeleccionado by remember { mutableStateOf("Todos") }
    var prioridadSeleccionada by remember { mutableStateOf("Todas") }
    var etiquetaSeleccionada by remember { mutableStateOf("Todas") }

    val formatoFecha = remember { SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()) }
    val listaTags by viewModel.tags.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(crema)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Lista de tareas", style = MaterialTheme.typography.headlineMedium, color = verdeOscuro)
            TextButton(onClick = { navController.navigate("crearTag") }) {
                Text("Gestionar Tags »", color = verdeOscuro)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { navController.navigate("nueva") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = verdeAgua)
        ) {
            Text("Nueva tarea")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = busqueda,
            onValueChange = { busqueda = it },
            label = { Text("Buscar tarea por título") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // NUEVO: BOTÓN LARGO DE ORDENAMIENTO (Mismo estilo que los filtros)
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                onClick = { menuOrdenExpanded = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = amarilloPastel)
            ) {
                Text("Ordenar por: $ordenSeleccionado")
            }
            DropdownMenu(
                expanded = menuOrdenExpanded,
                onDismissRequest = { menuOrdenExpanded = false },
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                DropdownMenuItem(text = { Text("Fecha de Creación") }, onClick = { ordenSeleccionado = "Fecha de Creación"; menuOrdenExpanded = false })
                DropdownMenuItem(text = { Text("Fecha de Vencimiento") }, onClick = { ordenSeleccionado = "Fecha de Vencimiento"; menuOrdenExpanded = false })
                DropdownMenuItem(text = { Text("Prioridad") }, onClick = { ordenSeleccionado = "Prioridad"; menuOrdenExpanded = false })
                DropdownMenuItem(text = { Text("Título") }, onClick = { ordenSeleccionado = "Título"; menuOrdenExpanded = false })
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // BOTONES DE FILTROS
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Box {
                OutlinedButton(onClick = { menuEstadoExpanded = true }, colors = ButtonDefaults.outlinedButtonColors(containerColor = amarilloPastel)) {
                    Text(if (estadoSeleccionado == "Todos") "Estado" else estadoSeleccionado)
                }
                DropdownMenu(expanded = menuEstadoExpanded, onDismissRequest = { menuEstadoExpanded = false }) {
                    DropdownMenuItem(text = { Text("Todos") }, onClick = { estadoSeleccionado = "Todos"; menuEstadoExpanded = false })
                    DropdownMenuItem(text = { Text("Completadas") }, onClick = { estadoSeleccionado = "Completadas"; menuEstadoExpanded = false })
                    DropdownMenuItem(text = { Text("Pendientes") }, onClick = { estadoSeleccionado = "Pendientes"; menuEstadoExpanded = false })
                }
            }

            Box {
                OutlinedButton(onClick = { menuPrioridadExpanded = true }, colors = ButtonDefaults.outlinedButtonColors(containerColor = amarilloPastel)) {
                    Text(if (prioridadSeleccionada == "Todas") "Prioridad" else prioridadSeleccionada)
                }
                DropdownMenu(expanded = menuPrioridadExpanded, onDismissRequest = { menuPrioridadExpanded = false }) {
                    DropdownMenuItem(text = { Text("Todas") }, onClick = { prioridadSeleccionada = "Todas"; menuPrioridadExpanded = false })
                    DropdownMenuItem(text = { Text("Alta") }, onClick = { prioridadSeleccionada = "Alta"; menuPrioridadExpanded = false })
                    DropdownMenuItem(text = { Text("Media") }, onClick = { prioridadSeleccionada = "Media"; menuPrioridadExpanded = false })
                    DropdownMenuItem(text = { Text("Baja") }, onClick = { prioridadSeleccionada = "Baja"; menuPrioridadExpanded = false })
                }
            }

            Box {
                OutlinedButton(onClick = { menuEtiquetaExpanded = true }, colors = ButtonDefaults.outlinedButtonColors(containerColor = amarilloPastel)) {
                    Text(if (etiquetaSeleccionada == "Todas") "Etiquetas" else etiquetaSeleccionada)
                }
                DropdownMenu(expanded = menuEtiquetaExpanded, onDismissRequest = { menuEtiquetaExpanded = false }) {
                    DropdownMenuItem(text = { Text("Todas") }, onClick = { etiquetaSeleccionada = "Todas"; menuEtiquetaExpanded = false })
                    listaTags.forEach { tag ->
                        DropdownMenuItem(text = { Text(tag.name) }, onClick = { etiquetaSeleccionada = tag.name; menuEtiquetaExpanded = false })
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        when (state) {
            is TareaState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator(color = verdeOscuro) }
            }
            is TareaState.Error -> {
                Text((state as TareaState.Error).message, color = MaterialTheme.colorScheme.error)
            }
            is TareaState.Success -> {
                val tareasCompletas = (state as TareaState.Success).tareas

                // PROCESAMIENTO DE FILTROS (REQUERIMIENTO 3 RESOLVIdO)
                val tareasFiltradas = tareasCompletas.filter { item ->
                    val matchesBusqueda = item.tarea.title.contains(busqueda, ignoreCase = true)
                    val matchesEstado = when (estadoSeleccionado) {
                        "Completadas" -> item.tarea.completed
                        "Pendientes" -> !item.tarea.completed
                        else -> true
                    }
                    val matchesPrioridad = if (prioridadSeleccionada == "Todas") true else item.tarea.priority == prioridadSeleccionada
                    val matchesEtiqueta = if (etiquetaSeleccionada == "Todas") true else item.tags.any { it.name == etiquetaSeleccionada }

                    matchesBusqueda && matchesEstado && matchesPrioridad && matchesEtiqueta
                }.sortedWith { o1, o2 ->
                    // PROCESAMIENTO DE ORDENAMIENTOS
                    when (ordenSeleccionado) {
                        "Título" -> o1.tarea.title.lowercase().compareTo(o2.tarea.title.lowercase())
                        "Prioridad" -> {
                            val peso = mapOf("Alta" to 1, "Media" to 2, "Baja" to 3)
                            (peso[o1.tarea.priority] ?: 2).compareTo(peso[o2.tarea.priority] ?: 2)
                        }
                        "Fecha de Vencimiento" -> {
                            val d1 = o1.tarea.dueDate.ifBlank { "99/99/9999" }
                            val d2 = o2.tarea.dueDate.ifBlank { "99/99/9999" }
                            d1.compareTo(d2)
                        }
                        else -> o1.tarea.createdAt.compareTo(o2.tarea.createdAt) // Fecha creación por defecto
                    }
                }

                LazyColumn(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(items = tareasFiltradas, key = { it.tarea.id }) { item ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = verdeSuave)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier.size(14.dp).background(
                                            when (item.tarea.priority) {
                                                "Alta" -> Color(0xFFFFB4A2)
                                                "Media" -> amarilloPastel
                                                else -> verdeAgua
                                            }, shape = RoundedCornerShape(4.dp)
                                        )
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(item.tarea.title, style = MaterialTheme.typography.titleLarge, color = verdeOscuro)
                                }

                                Spacer(modifier = Modifier.height(8.dp))
                                if (item.tarea.description.isNotEmpty()) Text(item.tarea.description)

                                Spacer(modifier = Modifier.height(6.dp))
                                if (item.tags.isNotEmpty()) {
                                    Text("Tags: " + item.tags.joinToString { it.name }, style = MaterialTheme.typography.bodySmall, color = verdeOscuro)
                                }

                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Añadido: ${formatoFecha.format(Date(item.tarea.createdAt))}")

                                // ARREGLADO: Mensaje condicional para fecha límite vacía
                                val vencimiento = item.tarea.dueDate.ifBlank { "Sin fecha límite" }
                                Text("Vencimiento: $vencimiento")

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(checked = item.tarea.completed, onCheckedChange = { viewModel.update(item.tarea.copy(completed = it)) })
                                    Text(if (item.tarea.completed) "Completada" else "Pendiente")
                                }

                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    FilledTonalButton(onClick = { navController.navigate("editar/${item.tarea.id}") }, colors = ButtonDefaults.filledTonalButtonColors(containerColor = amarilloPastel)) {
                                        Icon(Icons.Default.Edit, null)
                                        Text("Editar")
                                    }
                                    FilledTonalButton(onClick = { viewModel.delete(item.tarea) }, colors = ButtonDefaults.filledTonalButtonColors(containerColor = amarilloPastel)) {
                                        Icon(Icons.Default.Delete, null)
                                        Text("Eliminar")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}