package com.example.ualaapp.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class LoadingScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLoadingComponent() {
        composeTestRule.setContent {
            LoadingComponent(loadingText = "Cargando ciudades...")
        }

        composeTestRule.onNodeWithText("Cargando ciudades...").assertIsDisplayed()
    }
}