package com.example.ualaapp.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
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
                long = -34.599998
            )
        )

        assertNotNull(city)
        assertEquals("AR", city.country)
        assertEquals("Ciudad Autónoma de Buenos Aires", city.name)
        assertEquals(3433955, city._id)
        assertNotNull(city.coord)
        assertEquals(-58.450001, city.coord.lat, 0.0)
        assertEquals(-34.599998, city.coord.long, 0.0)
    }

    @Test
    fun initializeDto_Gualeguaychu() {
        val city = City(
            country = "AR",
            name = "Gualeguaychú",
            _id = 3433658,
            coord = Coordinates(
                lat = -58.51722,
                long = -33.00938
            )
        )

        assertNotNull(city)
        assertEquals("AR", city.country)
        assertEquals("Gualeguaychú", city.name)
        assertEquals(3433658, city._id)
        assertNotNull(city.coord)
        assertEquals(-58.51722, city.coord.lat, 0.0)
        assertEquals(-33.00938, city.coord.long, 0.0)
    }

    @Test
    fun deserialize_json_city() {


        assertNotNull(city)
        assertEquals("AR", city.country)
        assertEquals("Gualeguaychú", city.name)
        assertEquals(3433658, city._id)
        assertNotNull(city.coord)
        assertEquals(-58.51722, city.coord.lat, 0.0)
        assertEquals(-33.00938, city.coord.long, 0.0)
    }
}