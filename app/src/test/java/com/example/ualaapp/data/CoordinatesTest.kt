package com.example.ualaapp.data

import com.example.ualaapp.TestUtils
import org.junit.Assert.assertEquals
import org.junit.Test

class CoordinatesTest {

    @Test
    fun initializeDto_positiveCoords() {
        val coordinates = Coordinates(
            lat = 34.283333,
            lon = 44.549999
        )

        assertEquals(34.283333, coordinates.lat, 0.0)
        assertEquals(44.549999, coordinates.lon, 0.0)
    }

    @Test
    fun initializeDto_negativeCoords() {
        val coordinates = Coordinates(
            lat = -34.283333,
            lon = -44.549999
        )

        assertEquals(-34.283333, coordinates.lat, 0.0)
        assertEquals(-44.549999, coordinates.lon, 0.0)
    }

    @Test
    fun deserialize_json_coordinates() {
        val coordinates = TestUtils.deserialize<Coordinates>("coordinates.json")

        assertEquals(60.166672, coordinates.lat, 0.0)
        assertEquals(24.41667, coordinates.lon, 0.0)
    }
}