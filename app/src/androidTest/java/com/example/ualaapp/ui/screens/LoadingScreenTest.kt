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
    fun testLoadingComponent_defaultText() {
        composeTestRule.setContent {
            LoadingComponent(loadingText = "Cargando ciudades...")
        }

        composeTestRule.onNodeWithText("Cargando ciudades...").assertIsDisplayed()
    }

    @Test
    fun testLoadingComponent_someText() {
        composeTestRule.setContent {
            LoadingComponent(loadingText = "Some text")
        }

        composeTestRule.onNodeWithText("Some text").assertIsDisplayed()
    }

    @Test
    fun testRegisterComponent() {
        composeTestRule.setContent {
            RegisterComponent()
        }

        composeTestRule.onNodeWithText("Bienvenido").assertIsDisplayed()
        composeTestRule.onNodeWithText("Ingresar").assertIsDisplayed()
    }
}