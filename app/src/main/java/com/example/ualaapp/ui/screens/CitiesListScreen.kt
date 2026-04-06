package com.example.ualaapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ualaapp.R

@Composable
fun CitiesListScreen(modifier: Modifier = Modifier) {
    CitiesListComponent()
}

@Composable
fun CitiesListComponent(modifier: Modifier = Modifier) {
    LazyColumn {
        items(10) {
            CityItemComponent()
        }
    }
}

@Composable
fun CityItemComponent(
    modifier: Modifier = Modifier,
    isFavourite: Boolean = false
) {
    val favouriteContentDescription = if (isFavourite) "Favourite activated" else "Favourite deactivated"

    Row(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
        Column(
            modifier = Modifier.fillMaxWidth().weight(0.85f)
        ) {
            Text(
                text = "City, CountryCode",
                fontSize = 20.sp
            )
            Text(
                text = "latitude, longitude",
                modifier = Modifier.padding(top = 6.dp),
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
        Image(
            painter = painterResource(R.drawable.ic_favourite_unmarked),
            contentDescription = favouriteContentDescription,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterVertically)
                .weight(0.15f)
                .testTag("favourite_city_item")
        )
    }
    HorizontalDivider()
}

@Preview(showBackground = true)
@Composable
private fun CityItemComponent_Preview() {
    CityItemComponent()
}

@Preview(showBackground = true)
@Composable
private fun CitiesListScreen_Preview() {
    CitiesListScreen()
}