package com.example.practico3.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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

    val verdeAgua = Color(0xFFA8E6CF)

    val verdeSuave = Color(0xFFDCEDC1)

    val amarilloPastel = Color(0xFFFFF3B0)

    val crema = Color(0xFFFFFBF2)

    val verdeOscuro = Color(0xFF4E7C59)

    // CAMPOS

    var titulo by remember { mutableStateOf("") }

    var descripcion by remember { mutableStateOf("") }

    var etiqueta by remember { mutableStateOf("") }

    var busqueda by remember { mutableStateOf("") }

    // DROPDOWN

    var expanded by remember { mutableStateOf(false) }

    val prioridades = listOf(
        "Alta",
        "Media",
        "Baja"
    )

    var prioridadSeleccionada by remember {
        mutableStateOf("Media")
    }

    // TAREAS DEMO

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

    // PANTALLA

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(crema)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        // TITULO

        Text(
            text = "Lista de tareas",
            style = MaterialTheme.typography.headlineMedium,
            color = verdeOscuro
        )

        Spacer(modifier = Modifier.height(16.dp))

        // BUSCADOR

        OutlinedTextField(
            value = busqueda,
            onValueChange = {
                busqueda = it
            },
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

            OutlinedButton(
                onClick = {},
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = amarilloPastel
                )
            ) {
                Text("Estado")
            }

            OutlinedButton(
                onClick = {},
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = amarilloPastel
                )
            ) {
                Text("Prioridad")
            }

            OutlinedButton(
                onClick = {},
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = amarilloPastel
                )
            ) {
                Text("Etiquetas")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // FORMULARIO

        Text(
            text = "Nueva tarea",
            style = MaterialTheme.typography.titleLarge,
            color = verdeOscuro
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
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = verdeAgua,
                contentColor = Color.Black
            )
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
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = verdeSuave
                )
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
                                    when (tarea.priority) {

                                        "Alta" -> Color(0xFFFFB4A2)

                                        "Media" -> amarilloPastel

                                        else -> verdeAgua
                                    }
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
                            if (tarea.completed)
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
                            onClick = {},
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = amarilloPastel
                            )
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
                            onClick = {},
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = amarilloPastel
                            )
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
                            onClick = {},
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = amarilloPastel
                            )
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