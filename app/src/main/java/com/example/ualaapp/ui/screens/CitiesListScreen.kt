package com.example.ualaapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ualaapp.R
import com.example.ualaapp.data.City
import com.example.ualaapp.presentation.citieslist.CitiesListIntent
import com.example.ualaapp.presentation.citieslist.CitiesListViewModel

@Composable
fun CitiesListScreen(
    modifier: Modifier = Modifier,
    onCitySelected: (Int) -> Unit = {},
    viewModel: CitiesListViewModel = hiltViewModel()
) {
    val citiesStates by viewModel.citiesState.collectAsStateWithLifecycle()
    val filterState by viewModel.filterState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onEvent(CitiesListIntent.Get)
    }

    CitiesFilterListComponent(
        modifier = modifier,
        filterState = filterState,
        onValueChangeEvent = { newValue -> viewModel.onEvent(CitiesListIntent.Filter(newValue)) },
        listOfCities = citiesStates,
        onRowClickEvent = onCitySelected,
        onAddFavouriteClickEvent = {
            newValue -> viewModel.onEvent(CitiesListIntent.AddToFavourites(newValue))
        },
        onRemoveFavouriteClickEvent = {
            newValue -> viewModel.onEvent(CitiesListIntent.RemoveFromFavourites(newValue))
        }
    )
}

@Composable
fun CitiesFilterListComponent(
    modifier: Modifier = Modifier,
    filterState: String = "",
    onValueChangeEvent: (String) -> Unit = {},
    listOfCities: List<City>,
    onRowClickEvent: (Int) -> Unit = {},
    onAddFavouriteClickEvent: (Int) -> Unit = {},
    onRemoveFavouriteClickEvent: (Int) -> Unit = {}
) {
    Column(modifier = modifier) {
        CitiesFilterComponent(
            tfValue = filterState,
            onValueChangeEvent = { newValue -> onValueChangeEvent(newValue) }
        )
        CitiesListComponent(
            listOfCities = listOfCities,
            onRowClickEvent = onRowClickEvent,
            onAddFavouriteClickEvent = onAddFavouriteClickEvent,
            onRemoveFavouriteClickEvent = onRemoveFavouriteClickEvent
        )
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
fun CitiesListComponent(
    listOfCities: List<City> = emptyList(),
    onRowClickEvent: (Int) -> Unit = {},
    onAddFavouriteClickEvent: (Int) -> Unit = {},
    onRemoveFavouriteClickEvent: (Int) -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier
            .testTag("cities_list")
    ) {
        items(listOfCities) { city ->
            CityItemComponent(
                title = "${city.name}, ${city.country}",
                subtitle = "${city.coord.lat}, ${city.coord.lon}",
                isFavourite = city.isFavourite,
                onRowClickEvent = { onRowClickEvent(city._id) },
                onFavouriteClickEvent = { onAddFavouriteClickEvent(city._id) }
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
            .testTag("city_item_row")
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
                .size(36.dp)
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