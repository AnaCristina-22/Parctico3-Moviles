package com.example.practico3.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.practico3.viewmodel.TareaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagsScreen(viewModel: TareaViewModel) {

    var nombre by remember { mutableStateOf("") }

    // Recolectamos la lista de etiquetas desde la base de datos
    val tags by viewModel.tags.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Gestión de Etiquetas",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // SECCIÓN DE CREACIÓN
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre de la etiqueta") },
                modifier = Modifier.weight(1f) // Ocupa el espacio disponible
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    if (nombre.isNotBlank()) {
                        viewModel.crearTag(nombre.trim())
                        nombre = "" // Limpia el campo después de guardar
                    }
                },
                modifier = Modifier.height(56.dp) // Alinea la altura con el TextField
            ) {
                Text("Guardar")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // SECCIÓN DE LISTADO
        Text(
            text = "Mis Etiquetas:",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (tags.isEmpty()) {
            Text(
                text = "No tienes etiquetas creadas.",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            // LazyColumn para listar las etiquetas de forma eficiente
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = tags, key = { it.id }) { tag ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = tag.name,
                                style = MaterialTheme.typography.bodyLarge
                            )

                            // Botón de eliminar con el ícono de basura
                            IconButton(
                                onClick = { viewModel.deleteTag(tag) }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Eliminar etiqueta",
                                    tint = Color(0xFFD32F2F) // Un tono rojo para indicar eliminación
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


