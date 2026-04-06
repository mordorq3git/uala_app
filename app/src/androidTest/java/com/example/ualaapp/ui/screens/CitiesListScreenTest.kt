package com.example.ualaapp.ui.screens

import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class CitiesListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun cityItemComponent_default() {
        composeTestRule.setContent {
            CityItemComponent()
        }

        composeTestRule.onNodeWithText("City, CountryCode").assertIsDisplayed()
        composeTestRule.onNodeWithText("latitude, longitude").assertIsDisplayed()
        composeTestRule.onNodeWithTag("favourite_city_item").assertContentDescriptionEquals("Favourite deactivated").assertIsDisplayed()
    }

    @Test
    fun cityItemComponent_setFavourite() {
        composeTestRule.setContent {
            CityItemComponent(isFavourite = true)
        }

        composeTestRule.onNodeWithTag("favourite_city_item").assertContentDescriptionEquals("Favourite activated").assertIsDisplayed()
    }

    @Test
    fun cityItemComponent_unsetFavourite() {
        composeTestRule.setContent {
            CityItemComponent(isFavourite = false)
        }

        composeTestRule.onNodeWithTag("favourite_city_item").assertContentDescriptionEquals("Favourite deactivated").assertIsDisplayed()
    }
}