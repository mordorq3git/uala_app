package com.example.ualaapp.ui.navigation

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ualaapp.ui.screens.CitiesListScreen
import com.example.ualaapp.ui.screens.MapScreen

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptativeNavigationWrapper(modifier: Modifier = Modifier) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Nothing>()

    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            AnimatedPane {
                CitiesListScreen(modifier = modifier)
            }
        },
        detailPane = {
            AnimatedPane {
                MapScreen(modifier = modifier)
            }
        }
    )
}