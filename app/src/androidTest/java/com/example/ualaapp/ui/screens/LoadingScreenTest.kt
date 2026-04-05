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
            LoadingComponent()
        }

        composeTestRule.onNodeWithText("Cargando ciudades...").assertIsDisplayed()
    }

    @Test
    fun testRegisterComponent() {
        composeTestRule.setContent {
            RegisterComponent()
        }

        composeTestRule.onNodeWithText("Bienvenido").assertIsDisplayed()
        composeTestRule.onNodeWithText("Nombre de usuario").assertIsDisplayed()
        composeTestRule.onNodeWithText("Ingresar").assertIsDisplayed()
    }
}