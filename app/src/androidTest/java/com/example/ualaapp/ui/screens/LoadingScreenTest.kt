package com.example.ualaapp.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class LoadingScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLoadingComponent() {
        composeTestRule.setContent {
            LoadingComponent()
        }

        composeTestRule.onNodeWithText("Cargando ciudades...", substring = true, ignoreCase = true).assertIsDisplayed()
    }

    @Test
    fun testRegisterComponent() {
        composeTestRule.setContent {
            RegisterComponent()
        }

        composeTestRule.onNodeWithText("Bienvenido").assertIsDisplayed()
        composeTestRule.onNodeWithText("Nombre de usuario").assertIsDisplayed()
        composeTestRule.onNodeWithText("Ingresar").assertIsDisplayed().assertIsEnabled()
    }

    @Test
    fun testRegisterComponent_onChangeValue() {
        composeTestRule.setContent {
            RegisterComponent(userName = "John Doe")
        }

        composeTestRule.onNodeWithTag("register_with_username_textfield")
            .assertTextEquals("John Doe")
    }

    @Test
    fun testRegisterComponent_onChangeValue_setString() {
        var currentValue = ""

        composeTestRule.setContent {
            RegisterComponent(userName = "", onValueChangeEvent = { newValue -> currentValue = newValue })
        }

        composeTestRule.onNodeWithTag("register_with_username_textfield")
            .performTextInput("John Doe")

        assertEquals("John Doe", currentValue)
    }

    @Test
    fun testRegisterComponent_onChangeValue_initialized_setString() {
        var currentValue = ""

        composeTestRule.setContent {
            RegisterComponent(userName = "Jhone Doe", onValueChangeEvent = { newValue -> currentValue = newValue })
        }

        composeTestRule.onNodeWithTag("register_with_username_textfield")
            .performTextReplacement("mordorq3")

        assertEquals("mordorq3", currentValue)
    }
}