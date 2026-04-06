package com.example.ualaapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ualaapp.ui.screens.CitiesListScreen
import com.example.ualaapp.ui.screens.LoadingAndRegisterScreen
import kotlinx.serialization.Serializable

@Serializable
object Loading

@Serializable
object CitiesList

@Composable
fun NavigationWrapper(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Loading) {
        composable<Loading> {
            LoadingAndRegisterScreen(
                modifier = modifier
            )
        }

        composable<CitiesList> {
            AdaptativeNavigationWrapper(
                modifier = modifier
            )
        }
    }
}