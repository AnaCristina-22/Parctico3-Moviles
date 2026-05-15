package com.example.practico3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.practico3.screens.TareaScreen
import com.example.practico3.ui.theme.Practico3Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {

            Practico3Theme {

                TareaScreen()
            }
        }
    }
}