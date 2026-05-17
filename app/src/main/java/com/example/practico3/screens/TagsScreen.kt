package com.example.practico3.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.practico3.viewmodel.TareaViewModel

@Composable
fun TagsScreen(viewModel: TareaViewModel) {

    var nombre by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {

        Text("Crear Tag")

        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del tag") }
        )

        Button(onClick = {
            viewModel.crearTag(nombre)
            nombre = ""
        }) {
            Text("Guardar Tag")
        }
    }
}


