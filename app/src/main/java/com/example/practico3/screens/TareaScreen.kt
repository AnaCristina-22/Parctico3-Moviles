package com.example.practico3.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.practico3.data.entities.Tarea

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TareaScreen() {

    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var etiqueta by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }

    val prioridades = listOf(
        "Alta",
        "Media",
        "Baja"
    )

    var prioridadSeleccionada by remember {
        mutableStateOf("Media")
    }

    val tareas = remember {

        mutableStateListOf(

            Tarea(
                title = "Hacer práctico",
                description = "Terminar Compose",
                tag = "Universidad",
                priority = "Alta",
                completed = false
            ),

            Tarea(
                title = "Comprar comida",
                description = "Ir al mercado",
                tag = "Personal",
                priority = "Media",
                completed = true
            ),

            Tarea(
                title = "Estudiar Room",
                description = "Ver relaciones many to many",
                tag = "Android",
                priority = "Alta",
                completed = false
            ),

            Tarea(
                title = "Enviar informe",
                description = "Mandar PDF al docente",
                tag = "Trabajo",
                priority = "Baja",
                completed = true
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Text(
            text = "Lista de tareas",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // BUSCADOR

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = {
                Text("Buscar tarea")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // FILTROS

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            OutlinedButton(onClick = {}) {
                Text("Estado")
            }

            OutlinedButton(onClick = {}) {
                Text("Prioridad")
            }

            OutlinedButton(onClick = {}) {
                Text("Etiquetas")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // FORMULARIO

        Text(
            text = "Nueva tarea",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = titulo,
            onValueChange = {
                titulo = it
            },
            label = {
                Text("Título")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = descripcion,
            onValueChange = {
                descripcion = it
            },
            label = {
                Text("Descripción")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = etiqueta,
            onValueChange = {
                etiqueta = it
            },
            label = {
                Text("Etiqueta")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // PRIORIDAD

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {

            OutlinedTextField(
                value = prioridadSeleccionada,
                onValueChange = {},
                readOnly = true,
                label = {
                    Text("Prioridad")
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {

                prioridades.forEach { prioridad ->

                    DropdownMenuItem(
                        text = {
                            Text(prioridad)
                        },
                        onClick = {

                            prioridadSeleccionada = prioridad
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // FECHA

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = {
                Text("Fecha de vencimiento")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // BOTON CREAR

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Crear tarea")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // LISTA DE TAREAS

        tareas.forEach { tarea ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                shape = RoundedCornerShape(16.dp)
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    // TITULO + PRIORIDAD

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .size(14.dp)
                                .background(
                                    when(tarea.priority) {

                                        "Alta" -> Color.Red
                                        "Media" -> Color.Yellow
                                        else -> Color.Green
                                    }
                                )
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = tarea.title,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(tarea.description)

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Etiqueta: ${tarea.tag}")

                    Spacer(modifier = Modifier.height(4.dp))

                    Text("Prioridad: ${tarea.priority}")

                    Spacer(modifier = Modifier.height(4.dp))

                    Text("Fecha: 20/05/2026")

                    Spacer(modifier = Modifier.height(8.dp))

                    // CHECK COMPLETADO

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Checkbox(
                            checked = tarea.completed,
                            onCheckedChange = {}
                        )

                        Text(
                            if(tarea.completed)
                                "Completada"
                            else
                                "Pendiente"
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // BOTONES

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        FilledTonalButton(
                            onClick = {}
                        ) {

                            Icon(
                                Icons.Default.Edit,
                                contentDescription = null
                            )

                            Spacer(
                                modifier = Modifier.width(4.dp)
                            )

                            Text("Editar")
                        }

                        FilledTonalButton(
                            onClick = {}
                        ) {

                            Icon(
                                Icons.Default.Delete,
                                contentDescription = null
                            )

                            Spacer(
                                modifier = Modifier.width(4.dp)
                            )

                            Text("Eliminar")
                        }

                        FilledTonalButton(
                            onClick = {}
                        ) {

                            Icon(
                                Icons.Default.Info,
                                contentDescription = null
                            )

                            Spacer(
                                modifier = Modifier.width(4.dp)
                            )

                            Text("Detalles")
                        }
                    }
                }
            }
        }
    }
}