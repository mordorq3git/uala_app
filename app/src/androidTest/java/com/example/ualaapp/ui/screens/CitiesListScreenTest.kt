package com.example.ualaapp.ui.screens

import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import com.example.ualaapp.data.City
import com.example.ualaapp.data.Coordinates
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

        composeTestRule.onNodeWithText("Filter by City Name").assertIsDisplayed()
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
    fun citiesListComponent_withoutItems_byDefault() {
        composeTestRule.setContent {
            CitiesListComponent()
        }

        composeTestRule.onNodeWithTag("cities_list").assertIsNotDisplayed()
    }

    @Test
    fun citiesListComponent_withItems() {
        composeTestRule.setContent {
            val listOfCities = listOf(
                City(123, "City", "CTY", Coordinates(1.0, 2.0)),
                City(456, "City 2", "CTY 2", Coordinates(3.0, 4.0)),
                City(789, "City 3", "CTY 3", Coordinates(5.0, 6.0))
            )

            CitiesListComponent(
                listOfCities = listOfCities
            )
        }

        composeTestRule.onNodeWithTag("cities_list").assertIsDisplayed()
        composeTestRule.onNodeWithTag("cities_list")
            .onChildren()
            .filter(hasTestTag("city_item_row"))
            .assertCountEquals(3)
    }

    @Test
    fun citiesListComponent_withoutItems() {
        composeTestRule.setContent {
            CitiesListComponent(listOfCities = emptyList())
        }

        composeTestRule.onNodeWithTag("cities_list").assertIsNotDisplayed()
    }

    @Test
    fun citiesListComponent_onRowClickEvent() {
        var valueChanged = 0

        composeTestRule.setContent {
            val listOfCities = listOf(
                City(123, "City", "CTY", Coordinates(1.0, 2.0)),
                City(456, "City 2", "CTY 2", Coordinates(3.0, 4.0)),
                City(789, "City 3", "CTY 3", Coordinates(5.0, 6.0))
            )

            CitiesListComponent(
                listOfCities = listOfCities,
                onRowClickEvent = { _id -> valueChanged = _id }
            )
        }

        composeTestRule
            .onNodeWithTag("cities_list")
            .onChildren()
            .filter(hasTestTag("city_item_row"))
            .onFirst()
            .performClick()

        assertEquals(123, valueChanged)

        composeTestRule
            .onNodeWithTag("cities_list")
            .onChildren()
            .filter(hasTestTag("city_item_row"))
            .onLast()
            .performClick()

        assertEquals(789, valueChanged)
    }

    @Test
    fun citiesFilterListComponent_onRowClickEvent() {
        var valueChanged = 0

        composeTestRule.setContent {
            val listOfCities = listOf(
                City(2456, "City", "CTY", Coordinates(1.0, 2.0)),
                City(164, "City 2", "CTY 2", Coordinates(3.0, 4.0)),
                City(1212, "City 3", "CTY 3", Coordinates(5.0, 6.0))
            )

            CitiesFilterListComponent(
                listOfCities = listOfCities,
                onRowClickEvent = { _id -> valueChanged = _id }
            )
        }

        composeTestRule
            .onNodeWithTag("cities_list")
            .onChildren()
            .filter(hasTestTag("city_item_row"))
            .onFirst()
            .performClick()

        assertEquals(2456, valueChanged)

        composeTestRule
            .onNodeWithTag("cities_list")
            .onChildren()
            .filter(hasTestTag("city_item_row"))
            .onLast()
            .performClick()

        assertEquals(1212, valueChanged)
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