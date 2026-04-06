package com.example.ualaapp.ui.screens

import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertHasClickAction
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
            CityItemComponent(
                title = "City, CountryCode",
                subtitle = "latitude, longitude"
            )
        }

        composeTestRule.onNodeWithTag("city_item").assertHasClickAction()
        composeTestRule.onNodeWithText("City, CountryCode").assertIsDisplayed()
        composeTestRule.onNodeWithText("latitude, longitude").assertIsDisplayed()
        composeTestRule.onNodeWithTag("favourite_icon_city_item").assertContentDescriptionEquals("Favourite deactivated").assertIsDisplayed()
        composeTestRule.onNodeWithTag("favourite_icon_city_item").assertHasClickAction()
    }

    @Test
    fun cityItemComponent_otherValues() {
        composeTestRule.setContent {
            CityItemComponent(
                title = "City 2, CountryCode 2",
                subtitle = "latitude 2, longitude 2"
            )
        }

        composeTestRule.onNodeWithText("City 2, CountryCode 2").assertIsDisplayed()
        composeTestRule.onNodeWithText("latitude 2, longitude 2").assertIsDisplayed()
        composeTestRule.onNodeWithTag("favourite_icon_city_item").assertContentDescriptionEquals("Favourite deactivated").assertIsDisplayed()
    }

    @Test
    fun cityItemComponent_setFavourite() {
        composeTestRule.setContent {
            CityItemComponent(
                title = "City, CountryCode",
                subtitle = "latitude, longitude",
                isFavourite = true
            )
        }

        composeTestRule.onNodeWithTag("favourite_icon_city_item").assertContentDescriptionEquals("Favourite activated").assertIsDisplayed()
    }

    @Test
    fun cityItemComponent_unsetFavourite() {
        composeTestRule.setContent {
            CityItemComponent(
                title = "City, CountryCode",
                subtitle = "latitude, longitude",
                isFavourite = false
            )
        }

        composeTestRule.onNodeWithTag("favourite_icon_city_item").assertContentDescriptionEquals("Favourite deactivated").assertIsDisplayed()
    }
}