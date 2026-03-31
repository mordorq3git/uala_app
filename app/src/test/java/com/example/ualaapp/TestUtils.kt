package com.example.ualaapp

import kotlinx.serialization.json.Json

class TestUtils {
    companion object {
        inline fun <reified T>deserialize(fileName: String): T {
            val jsonString = TestUtils::class.java.classLoader
                ?.getResource(fileName)?.readText()
                ?: throw Exception("File not found")

            return Json.decodeFromString<T>(jsonString)
        }
    }
}