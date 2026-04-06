package com.example.ualaapp.ui.screens

import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class CitiesListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun filterItemComponent_default() {
        composeTestRule.setContent {
            CitiesFilterComponent()
        }

        composeTestRule.onNodeWithText("Filter city").assertIsDisplayed()
    }

    @Test
    fun filterItemComponent_setCustomValue() {
        composeTestRule.setContent {
            CitiesFilterComponent(tfValue = "Gualeguaychu, AR")
        }

        composeTestRule.onNodeWithText("Gualeguaychu, AR").assertIsDisplayed()
    }

    @Test
    fun filterItemComponent_onValueChangeEvent() {
        var currentValue = "Gualeguaychu, AR"

        composeTestRule.setContent {
            CitiesFilterComponent(onValueChangeEvent = { newValue -> currentValue = newValue })
        }

        composeTestRule.onNodeWithTag("city_filter").performTextReplacement("Buenos Aires, AR")

        assertEquals("Buenos Aires, AR", currentValue)
    }

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

    @Test
    fun cityItemComponent_clicOnRow_event() {
        var itemClicked = false

        composeTestRule.setContent {
            CityItemComponent(
                title = "City, CountryCode",
                subtitle = "latitude, longitude",
                onRowClickEvent = { itemClicked = true }
            )
        }

        composeTestRule.onNodeWithTag("city_item").assertHasClickAction()
        composeTestRule.onNodeWithTag("city_item").performClick()
        assertTrue(itemClicked)
    }

    @Test
    fun cityItemComponent_clicOnFavourite_event() {
        var favouriteClicked = false

        composeTestRule.setContent {
            CityItemComponent(
                title = "City, CountryCode",
                subtitle = "latitude, longitude",
                onFavouriteClickEvent = { favouriteClicked = true }
            )
        }

        composeTestRule.onNodeWithTag("favourite_icon_city_item").assertHasClickAction()
        composeTestRule.onNodeWithTag("favourite_icon_city_item").performClick()
        assertTrue(favouriteClicked)
    }
}