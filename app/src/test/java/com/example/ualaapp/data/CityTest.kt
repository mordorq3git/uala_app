package com.example.ualaapp.data

import com.example.ualaapp.utils.TestUtils
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CityTest {

    @Test
    fun initializeDto_BsAs() {
        val city = City(
            country = "AR",
            name = "Ciudad Autónoma de Buenos Aires",
            _id = 3433955,
            coord = Coordinates(
                lat = -58.450001,
                lon = -34.599998
            )
        )

        assertNotNull(city)
        assertEquals("AR", city.country)
        assertEquals("Ciudad Autónoma de Buenos Aires", city.name)
        assertEquals(3433955, city._id)
        assertNotNull(city.coord)
        assertEquals(-58.450001, city.coord.lat, 0.0)
        assertEquals(-34.599998, city.coord.lon, 0.0)
        assertFalse(city.isFavourite)
    }

    @Test
    fun initializeDto_Gualeguaychu() {
        val city = City(
            country = "AR",
            name = "Gualeguaychú",
            _id = 3433658,
            coord = Coordinates(
                lat = -58.51722,
                lon = -33.00938
            )
        )

        assertNotNull(city)
        assertEquals("AR", city.country)
        assertEquals("Gualeguaychú", city.name)
        assertEquals(3433658, city._id)
        assertNotNull(city.coord)
        assertEquals(-58.51722, city.coord.lat, 0.0)
        assertEquals(-33.00938, city.coord.lon, 0.0)
        assertFalse(city.isFavourite)
    }

    @Test
    fun deserialize_json_city() {
        val city = TestUtils.deserialize<City>("city.json")

        assertNotNull(city)
        assertEquals("FI", city.country)
        assertEquals("Kirkkonummi", city.name)
        assertEquals(649631, city._id)
        assertNotNull(city.coord)
        assertEquals(60.166672, city.coord.lat, 0.0)
        assertEquals(24.41667, city.coord.lon, 0.0)
        assertFalse(city.isFavourite)
    }

    @Test
    fun initializeDto_Gualeguaychu_favorite() {
        val city = City(
            country = "AR",
            name = "Gualeguaychú",
            _id = 3433658,
            coord = Coordinates(
                lat = -58.51722,
                lon = -33.00938
            ),
            isFavourite = true
        )

        assertNotNull(city)
        assertEquals("AR", city.country)
        assertEquals("Gualeguaychú", city.name)
        assertEquals(3433658, city._id)
        assertNotNull(city.coord)
        assertEquals(-58.51722, city.coord.lat, 0.0)
        assertEquals(-33.00938, city.coord.lon, 0.0)
        assertTrue(city.isFavourite)
    }
}