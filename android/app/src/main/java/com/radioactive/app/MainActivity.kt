package com.radioactive.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                RadioActivePrototype()
            }
        }
    }
}

data class Station(
    val id: String,
    val name: String,
    val country: String,
    val city: String,
    val style: String
)

@Composable
private fun RadioActivePrototype() {
    val stations = remember {
        listOf(
            Station("1", "Radio Madrid Hits", "España", "Madrid", "Pop"),
            Station("2", "New York Jazz FM", "Estados Unidos", "New York", "Jazz"),
            Station("3", "Berlin Techno Live", "Alemania", "Berlin", "Electrónica"),
            Station("4", "Sao Paulo Rock", "Brasil", "São Paulo", "Rock"),
            Station("5", "Tokyo Chill Waves", "Japón", "Tokyo", "LoFi")
        )
    }

    val favorites = remember { mutableStateListOf<String>() }

    var query by remember { mutableStateOf("") }
    var selectedCountry by remember { mutableStateOf("Todos") }
    var selectedCity by remember { mutableStateOf("Todas") }
    var selectedStyle by remember { mutableStateOf("Todos") }

    val filteredStations = stations.filter { station ->
        val matchQuery = query.isBlank() ||
            station.name.contains(query, ignoreCase = true) ||
            station.country.contains(query, ignoreCase = true) ||
            station.city.contains(query, ignoreCase = true) ||
            station.style.contains(query, ignoreCase = true)

        val matchCountry = selectedCountry == "Todos" || station.country == selectedCountry
        val matchCity = selectedCity == "Todas" || station.city == selectedCity
        val matchStyle = selectedStyle == "Todos" || station.style == selectedStyle

        matchQuery && matchCountry && matchCity && matchStyle
    }

    val countries = listOf("Todos") + stations.map { it.country }.distinct().sorted()
    val cities = listOf("Todas") + stations.map { it.city }.distinct().sorted()
    val styles = listOf("Todos") + stations.map { it.style }.distinct().sorted()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("RadioActive") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Buscar por emisora, país, ciudad o estilo") },
                modifier = Modifier.fillMaxWidth()
            )

            FilterRow(
                title = "País",
                options = countries,
                selected = selectedCountry,
                onSelect = { selectedCountry = it }
            )
            FilterRow(
                title = "Ciudad",
                options = cities,
                selected = selectedCity,
                onSelect = { selectedCity = it }
            )
            FilterRow(
                title = "Estilo",
                options = styles,
                selected = selectedStyle,
                onSelect = { selectedStyle = it }
            )

            Text(
                text = "Resultados: ${filteredStations.size}",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                items(filteredStations, key = { it.id }) { station ->
                    val isFavorite = station.id in favorites
                    StationCard(
                        station = station,
                        isFavorite = isFavorite,
                        onToggleFavorite = {
                            if (isFavorite) favorites.remove(station.id) else favorites.add(station.id)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun FilterRow(
    title: String,
    options: List<String>,
    selected: String,
    onSelect: (String) -> Unit
) {
    Column(modifier = Modifier.padding(top = 10.dp)) {
        Text(text = title, fontWeight = FontWeight.Bold)
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(options) { option ->
                AssistChip(
                    onClick = { onSelect(option) },
                    label = { Text(option) }
                )
            }
        }
    }
}

@Composable
private fun StationCard(
    station: Station,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(station.name, fontWeight = FontWeight.Bold)
                Text("${station.country} · ${station.city} · ${station.style}")
            }
            IconButton(onClick = onToggleFavorite) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = if (isFavorite) "Quitar de favoritos" else "Añadir a favoritos"
                )
            }
        }
    }
}
