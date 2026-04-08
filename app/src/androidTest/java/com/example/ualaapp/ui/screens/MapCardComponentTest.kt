package com.example.ualaapp.ui.screens

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class MapCardComponentTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun showCity() {
        composeTestRule.setContent {
            MapCardComponent(city = "Gualeguaychú")
        }

        composeTestRule.onNodeWithText("Gualeguaychú").assertIsDisplayed()
    }

    @Test
    fun showCountryName() {
        composeTestRule.setContent {
            MapCardComponent(country = "AR")
        }

        composeTestRule.onNodeWithText("AR").assertIsDisplayed()
    }

    @Test
    fun showLatitude() {
        composeTestRule.setContent {
            MapCardComponent(
                lat = 0.65465,
                lon = 1.5588
            )
        }

        composeTestRule.onNodeWithText("Lat: 0.65465, Lon: 1.5588").assertIsDisplayed()
    }

    @Test
    fun verifyClickEvent() {
        var eventClicked = false

        composeTestRule.setContent {
            MapCardComponent(onFavoriteClick = {
                eventClicked = true
            })
        }

        composeTestRule.onNodeWithTag("favorite_icon_map_card").assertHasClickAction().performClick()
        assertTrue(eventClicked)
    }

    @Test
    fun favoriteEnabled() {
        composeTestRule.setContent {
            MapCardComponent(
                isFavorite = true
            )
        }

        composeTestRule.onNodeWithContentDescription("Quitar de favoritos").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Agregar a favoritos").assertIsNotDisplayed()
    }

    @Test
    fun favoriteDisabled() {
        composeTestRule.setContent {
            MapCardComponent(
                isFavorite = false
            )
        }

        composeTestRule.onNodeWithContentDescription("Agregar a favoritos").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Quitar de favoritos").assertIsNotDisplayed()

    }
}