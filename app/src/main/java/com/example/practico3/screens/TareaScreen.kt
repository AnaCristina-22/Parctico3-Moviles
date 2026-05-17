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
import java.util.Date
import java.util.Locale

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

    // Estados para el control y apertura de los menús de filtros
    var menuEstadoExpanded by remember { mutableStateOf(false) }
    var menuPrioridadExpanded by remember { mutableStateOf(false) }
    var menuEtiquetaExpanded by remember { mutableStateOf(false) }

    // Valores seleccionados de los filtros
    var estadoSeleccionado by remember { mutableStateOf("Todos") }
    var prioridadSeleccionada by remember { mutableStateOf("Todas") }
    var etiquetaSeleccionada by remember { mutableStateOf("Todas") }

    // Formateador para convertir los milisegundos (Long) a una fecha legible
    val formatoFecha = remember { SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()) }
    val listaTags by viewModel.tags.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(crema)
            .padding(16.dp)
    ) {
        // CABECERA CON TÍTULO Y BOTÓN DE TAGS EN LA ESQUINA DERERCHA
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Lista de tareas",
                style = MaterialTheme.typography.headlineMedium,
                color = verdeOscuro
            )

            // Pequeño botón para ir a la sección de creación de Etiquetas
            TextButton(
                onClick = { navController.navigate("crearTag") },
                colors = ButtonDefaults.textButtonColors(contentColor = verdeOscuro)
            ) {
                Text("Gestionar Tags »", style = MaterialTheme.typography.labelLarge)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // BOTÓN NUEVA TAREA
        Button(
            onClick = { navController.navigate("nueva") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = verdeAgua)
        ) {
            Text("Nueva tarea")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // BUSCADOR POR TEXTO
        OutlinedTextField(
            value = busqueda,
            onValueChange = { busqueda = it },
            label = { Text("Buscar tarea por título") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // BOTONES DE FILTROS CON MOVIMIENTO Y MENÚ DESPLEGABLE
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Filtro por Estado
            Box {
                OutlinedButton(
                    onClick = { menuEstadoExpanded = true },
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = amarilloPastel)
                ) {
                    Text(if (estadoSeleccionado == "Todos") "Estado" else estadoSeleccionado)
                }
                DropdownMenu(
                    expanded = menuEstadoExpanded,
                    onDismissRequest = { menuEstadoExpanded = false }
                ) {
                    DropdownMenuItem(text = { Text("Todos") }, onClick = { estadoSeleccionado = "Todos"; menuEstadoExpanded = false })
                    DropdownMenuItem(text = { Text("Completadas") }, onClick = { estadoSeleccionado = "Completadas"; menuEstadoExpanded = false })
                    DropdownMenuItem(text = { Text("Pendientes") }, onClick = { estadoSeleccionado = "Pendientes"; menuEstadoExpanded = false })
                }
            }

            // Filtro por Prioridad
            Box {
                OutlinedButton(
                    onClick = { menuPrioridadExpanded = true },
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = amarilloPastel)
                ) {
                    Text(if (prioridadSeleccionada == "Todas") "Prioridad" else prioridadSeleccionada)
                }
                DropdownMenu(
                    expanded = menuPrioridadExpanded,
                    onDismissRequest = { menuPrioridadExpanded = false }
                ) {
                    DropdownMenuItem(text = { Text("Todas") }, onClick = { prioridadSeleccionada = "Todas"; menuPrioridadExpanded = false })
                    DropdownMenuItem(text = { Text("Alta") }, onClick = { prioridadSeleccionada = "Alta"; menuPrioridadExpanded = false })
                    DropdownMenuItem(text = { Text("Media") }, onClick = { prioridadSeleccionada = "Media"; menuPrioridadExpanded = false })
                    DropdownMenuItem(text = { Text("Baja") }, onClick = { prioridadSeleccionada = "Baja"; menuPrioridadExpanded = false })
                }
            }

            // Filtro por Etiquetas (Visual)
            Box {
                OutlinedButton(
                    onClick = { menuEtiquetaExpanded = true },
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = amarilloPastel)
                ) {
                    Text(if (etiquetaSeleccionada == "Todas") "Etiquetas" else etiquetaSeleccionada)
                }
                DropdownMenu(
                    expanded = menuEtiquetaExpanded,
                    onDismissRequest = { menuEtiquetaExpanded = false }
                ) {
                    DropdownMenuItem(text = { Text("Todas") }, onClick = { etiquetaSeleccionada = "Todas"; menuEtiquetaExpanded = false })
                    listaTags.forEach { tag ->
                        DropdownMenuItem(text = { Text(tag.name) }, onClick = { etiquetaSeleccionada = tag.name; menuEtiquetaExpanded = false })
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // CONTROL DE ESTADOS REACTIVOS
        when (state) {
            is TareaState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = verdeOscuro)
                }
            }
            is TareaState.Error -> {
                Text(
                    text = (state as TareaState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(8.dp)
                )
            }
            is TareaState.Success -> {
                val tareas = (state as TareaState.Success).tareas

                // PROCESAMIENTO Y CRUCE DE FILTROS EN TIEMPO REAL
                val tareasFiltradas = tareas.filter { tarea ->
                    val matchesBusqueda = tarea.title.contains(busqueda, ignoreCase = true)

                    val matchesEstado = when (estadoSeleccionado) {
                        "Completadas" -> tarea.completed
                        "Pendientes" -> !tarea.completed
                        else -> true
                    }

                    val matchesPrioridad = when (prioridadSeleccionada) {
                        "Todas" -> true
                        else -> tarea.priority == prioridadSeleccionada
                    }

                    matchesBusqueda && matchesEstado && matchesPrioridad
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(items = tareasFiltradas, key = { it.id }) { tarea ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = verdeSuave)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(14.dp)
                                            .background(
                                                when (tarea.priority) {
                                                    "Alta" -> Color(0xFFFFB4A2)
                                                    "Media" -> amarilloPastel
                                                    else -> verdeAgua
                                                },
                                                shape = RoundedCornerShape(4.dp)
                                            )
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text(
                                        text = tarea.title,
                                        style = MaterialTheme.typography.titleLarge,
                                        color = verdeOscuro
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                if (tarea.description.isNotEmpty()) {
                                    Text(tarea.description)
                                    Spacer(modifier = Modifier.height(8.dp))
                                }

                                Text("Prioridad: ${tarea.priority}")
                                Spacer(modifier = Modifier.height(4.dp))

                                // Muestra la fecha exacta en la que se añadió la tarea a la BD
                                val fechaFormateada = remember(tarea.createdAt) {
                                    formatoFecha.format(Date(tarea.createdAt))
                                }
                                Text("Añadido el: $fechaFormateada")

                                Spacer(modifier = Modifier.height(4.dp))

                                // Validación de Fecha Limite Opcional
                                val vencimientoTexto = if (tarea.dueDate.isNullOrBlank()) {
                                    "Sin fecha límite"
                                } else {
                                    tarea.dueDate
                                }
                                Text("Vencimiento: $vencimientoTexto")

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(
                                        checked = tarea.completed,
                                        onCheckedChange = { isChecked ->
                                            viewModel.update(tarea.copy(completed = isChecked))
                                        }
                                    )
                                    Text(if (tarea.completed) "Completada" else "Pendiente")
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    FilledTonalButton(
                                        onClick = {
                                            navController.navigate("editar/${tarea.id}")
                                        },
                                        colors = ButtonDefaults.filledTonalButtonColors(containerColor = amarilloPastel)
                                    ) {
                                        Icon(Icons.Default.Edit, contentDescription = null)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Editar")
                                    }

                                    FilledTonalButton(
                                        onClick = {
                                            viewModel.delete(tarea)
                                        },
                                        colors = ButtonDefaults.filledTonalButtonColors(containerColor = amarilloPastel)
                                    ) {
                                        Icon(Icons.Default.Delete, contentDescription = null)
                                        Spacer(modifier = Modifier.width(4.dp))
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