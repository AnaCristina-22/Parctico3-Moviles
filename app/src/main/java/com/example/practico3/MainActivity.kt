package com.example.practico3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHost
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.practico3.data.db.AppDatabase
import com.example.practico3.repository.TareaRepository
import com.example.practico3.screens.EditarScreen
import com.example.practico3.screens.NuevaTareaScreen
import com.example.practico3.screens.TareaScreen
import com.example.practico3.ui.theme.Practico3Theme
import com.example.practico3.viewmodel.TareaViewModel
import com.example.practico3.screens.TagsScreen

class MainActivity : ComponentActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            val db = AppDatabase.getDatabase(this)
            val repo = TareaRepository(db.tareaDao())
            val viewModel = TareaViewModel(repo)

            setContent {
                Practico3Theme {

                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "lista"
                    ) {

                        composable("lista") {
                            TareaScreen(viewModel, navController)
                        }

                        composable("nueva") {
                            NuevaTareaScreen(viewModel, navController)
                        }

                        composable(
                            route = "editar/{tareaId}",
                            arguments = listOf(navArgument("tareaId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val tareaId = backStackEntry.arguments?.getInt("tareaId") ?: 0
                            EditarScreen(
                                tareaId = tareaId,
                                viewModel = viewModel,
                                navController = navController
                            )
                        }

                        composable("crearTag") {
                            TagsScreen(viewModel)
                        }

                    }
                }
            }
        }
}