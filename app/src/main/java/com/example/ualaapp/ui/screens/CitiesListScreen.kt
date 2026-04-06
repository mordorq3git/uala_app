package com.example.ualaapp.ui.screens

import android.icu.text.CaseMap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ualaapp.R

@Composable
fun CitiesListScreen(modifier: Modifier = Modifier) {
    CitiesFilterListComponent(modifier)
}

@Composable
fun CitiesFilterListComponent(modifier: Modifier = Modifier) {
    Column {
        CitiesFilterComponent()
        CitiesListComponent()
    }
}

@Composable
fun CitiesFilterComponent(
    tfValue: String = "",
    onValueChangeEvent: (String) -> Unit = {}
) {
    TextField(
        value = tfValue,
        onValueChange = { newValue ->
            onValueChangeEvent(newValue)
        },
        modifier = Modifier
            .fillMaxWidth()
            .testTag("city_filter"),
        placeholder = {
            Text(
                text = stringResource(R.string.filter_city)
            )
        }
    )
}

@Composable
fun CitiesListComponent() {
    LazyColumn {
        items(10) {
            CityItemComponent(
                title = "City, CountryCode",
                subtitle = "latitude, longitude"
            )
        }
    }
}

@Composable
fun CityItemComponent(
    title: String,
    subtitle: String,
    isFavourite: Boolean = false,
    onRowClickEvent: () -> Unit = {},
    onFavouriteClickEvent: () -> Unit = {}
) {
    val favouriteContentDescription = if (isFavourite) "Favourite activated" else "Favourite deactivated"
    val favouriteImage = if (isFavourite) R.drawable.ic_favourite_activated else R.drawable.ic_favourite_deactivated

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp, 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.85f)
                .clickable(enabled = true, onClick = {
                    onRowClickEvent()
                })
                .testTag("city_item")
        ) {
            Text(
                text = title,
                fontSize = 20.sp
            )
            Text(
                text = subtitle,
                modifier = Modifier
                    .padding(top = 6.dp),
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
        Image(
            painter = painterResource(favouriteImage),
            contentDescription = favouriteContentDescription,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterVertically)
                .weight(0.15f)
                .clickable(enabled = true, onClick = {
                    onFavouriteClickEvent()
                })
                .testTag("favourite_icon_city_item")
        )
    }
    HorizontalDivider()
}

@Preview(showBackground = true)
@Composable
private fun CitiesFilterComponent_Preview() {
    CitiesFilterComponent()
}

@Preview(showBackground = true)
@Composable
private fun CityItemComponent_Preview() {
    CityItemComponent(
        title = "City, CountryCode",
        subtitle = "latitude, longitude"
    )
}

@Preview(showBackground = true)
@Composable
private fun CityItemComponent_favourite_Preview() {
    CityItemComponent(
        title = "City, CountryCode",
        subtitle = "latitude, longitude",
        isFavourite = true
    )
}

@Preview(showBackground = true)
@Composable
private fun CitiesListScreen_Preview() {
    CitiesListScreen()
}