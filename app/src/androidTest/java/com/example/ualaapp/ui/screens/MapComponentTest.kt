package com.example.ualaapp.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.example.ualaapp.data.City
import com.example.ualaapp.data.Coordinates
import org.junit.Rule
import org.junit.Test

class MapComponentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun verifyCardIsNotShowed_whenCityIsNull() {
        composeTestRule.setContent {
            MapComponent(
                city = null
            )
        }

        composeTestRule.onNodeWithTag("map_card").assertIsNotDisplayed()
    }

    @Test
    fun verifyCardIsShowed_whenCityIsNotNull() {
        composeTestRule.setContent {
            MapComponent(
                city = City(123, "City", "CTRY", Coordinates(0.65465, 1.5588))
            )
        }

        composeTestRule.onNodeWithTag("map_card").assertIsDisplayed()
    }

    @Test
    fun verifyCardIsShowed_integration() {
        composeTestRule.setContent {
            MapComponent(
                city = City(123, "City", "CTRY", Coordinates(0.65465, 1.5588))
            )
        }

        composeTestRule.onNodeWithTag("map_card").assertIsDisplayed()

        composeTestRule.onNodeWithText("City").assertIsDisplayed()
        composeTestRule.onNodeWithText("CTRY").assertIsDisplayed()
        composeTestRule.onNodeWithText("Lat: 0.65465, Lon: 1.5588").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Quitar de favoritos").assertIsNotDisplayed()
        composeTestRule.onNodeWithContentDescription("Agregar a favoritos").assertIsDisplayed()
    }
}