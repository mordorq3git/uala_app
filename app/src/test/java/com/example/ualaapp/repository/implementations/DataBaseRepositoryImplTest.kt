package com.example.ualaapp.repository.implementations

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class)
class DataBaseRepositoryImplTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    @Inject
    lateinit var repository: DataBaseRepositoryImpl

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun getCities_returnsEmptyList() = runTest {
        val cities = repository.getCities()

        Assert.assertNotNull(cities)
        Assert.assertTrue(cities.isEmpty())
    }
}