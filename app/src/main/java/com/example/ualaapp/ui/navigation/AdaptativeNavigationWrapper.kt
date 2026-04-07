package com.example.ualaapp.ui.navigation

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.ualaapp.ui.screens.CitiesListScreen
import com.example.ualaapp.ui.screens.MapScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptativeNavigationWrapper(modifier: Modifier = Modifier) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Int>()
    val scope = rememberCoroutineScope()

    BackHandler(enabled = navigator.canNavigateBack()) {
        scope.launch {
            navigator.navigateBack()
        }
    }

    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            AnimatedPane {
                CitiesListScreen(
                    modifier = modifier,
                    onCitySelected = { city_id ->
                        scope.launch {
                            navigator.navigateTo(
                                ListDetailPaneScaffoldRole.Detail,
                                city_id
                            )
                        }
                    }
                )
            }
        },
        detailPane = {
            AnimatedPane {
                val selectedCityId = navigator.currentDestination?.contentKey ?: -1

                MapScreen(modifier = modifier, selectedCityId = selectedCityId)
            }
        }
    )
}