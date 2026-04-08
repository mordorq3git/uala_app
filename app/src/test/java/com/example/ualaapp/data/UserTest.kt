package com.example.ualaapp.data

import org.junit.Assert.assertEquals
import org.junit.Test

class UserTest {

    @Test
    fun initializeInstance() {
        val user = User(
            id_user = 123,
            name = "John Doe"
        )

        assertEquals(123, user.id_user)
        assertEquals("John Doe", user.name)
    }
}