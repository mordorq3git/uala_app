package com.example.ualaapp.data

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
}