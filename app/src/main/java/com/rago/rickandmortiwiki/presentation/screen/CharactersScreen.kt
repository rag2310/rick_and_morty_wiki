package com.rago.rickandmortiwiki.presentation.screen

import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rago.rickandmortiwiki.R
import com.rago.rickandmortiwiki.data.model.Result
import com.rago.rickandmortiwiki.data.model.seedResult
import com.rago.rickandmortiwiki.presentation.ui.composables.ErrorDialog
import com.rago.rickandmortiwiki.presentation.ui.composables.LoadingDialog
import com.rago.rickandmortiwiki.presentation.uistate.CharactersUIState

@Composable
fun CharactersScreen(charactersUIState: CharactersUIState) {

    //Dialogo de carga
    LoadingDialog(showDialog = charactersUIState.loading)

    // Dialogo de error que usa los recursos del sistema para mensajes generales.
    ErrorDialog(errorRes = charactersUIState.errorRes) {
        charactersUIState.onClearError()
    }

    // Dialogo de error que usa mensaje directo del servidor(Si lo hay).
    ErrorDialog(errorMsg = charactersUIState.error) {
        charactersUIState.onClearError()
    }

    //Al iniciar la pantalla obtienen la primera pagina de los personajes.
    LaunchedEffect(key1 = Unit) {
        charactersUIState.getCharacters()
    }

    CharactersScreenContent(charactersUIState)
}

@Preview(showBackground = true)
@Composable
private fun CharactersScreenContent(
    charactersUIState: CharactersUIState = CharactersUIState(
        characters = listOf(seedResult)
    )
) {

    val listState = rememberLazyListState()

    LaunchedEffect(charactersUIState.currentIndex) {
        val currentIndex = charactersUIState.currentIndex
        if (currentIndex > 0) {
            listState.scrollToItem(currentIndex)
        }
    }
    // Maneja animacion de cuando se cambio el valor de la lista de characters.
    Crossfade(targetState = charactersUIState.characters, label = "list_characters") {
        if (it.isEmpty()) {
            CharactersScreenEmptyData(charactersUIState)
        } else {
            LazyColumn(Modifier.fillMaxSize(), state = listState) {
                items(charactersUIState.characters) { result ->
                    ItemCharacter(result)
                }
                if (charactersUIState.characters.isNotEmpty() && (charactersUIState.currentPage <= charactersUIState.totalPage)) {
                    item {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            Button(
                                onClick = charactersUIState.getCharacters
                            ) {
                                Text(text = stringResource(id = R.string.loading_more))
                            }
                        }
                    }
                }
            }
        }
    }
}

// Pantalla de que no se logro obtener datos.
@Preview(showBackground = true)
@Composable
private fun CharactersScreenEmptyData(charactersUIState: CharactersUIState = CharactersUIState()) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiary),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Filled.Inbox, contentDescription = null, modifier = Modifier.size(64.dp), tint = MaterialTheme.colorScheme.secondary)
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.not_data),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = { charactersUIState.getCharacters() }) {
                Text(text = stringResource(id = R.string.again))
            }
        }
    }
}

// Card que maneja los datos de los personajes
@Preview(showBackground = true)
@Composable
private fun ItemCharacter(
    result: Result = seedResult
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 6.dp), colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.onTertiaryContainer
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
        ) {

            // Se carga la imagen del personaje usando la libreria de Coil.
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(result.image ?: "")
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.place_holder),
                contentDescription = stringResource(R.string.description),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(128.dp)
                    .fillMaxSize()
            )
            Column(
                modifier = Modifier.padding(start = 10.dp)
            ) {
                TitleText(result.name ?: "")
                RowStatusAndSpecies(
                    status = result.status?.lowercase() ?: "",
                    species = result.species ?: ""
                )
                LabelText(res = R.string.last_known_location)
                BodyText(body = result.location?.name ?: "")
                LabelText(res = R.string.first_seen_in)
                BodyText(body = result.origin?.name ?: "")
            }
        }
    }
}

@Composable
private fun TitleText(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun LabelText(@StringRes res: Int) {
    Text(
        modifier = Modifier.padding(top = 6.dp),
        text = stringResource(id = res),
        style = MaterialTheme.typography.labelMedium, color = Color.White
    )
}

@Composable
private fun BodyText(body: String) {
    Text(
        text = body,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.secondary
    )
}

@Composable
private fun RowStatusAndSpecies(status: String, species: String) {
    // se selecciona un color usando el status del personaje, "vivo" = "verde", "muerto" = "rojo" y "desconocido" = "gris"
    val statusColor = when (status) {
        "alive" -> {
            Color.Green
        }

        "dead" -> {
            Color.Red
        }

        else -> Color.Gray
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(statusColor)
        )
        Spacer(modifier = Modifier.width(6.dp))
        NormalText(text = status)
        NormalText(text = " - ")
        NormalText(text = species)
    }
}

@Composable
private fun NormalText(text:String){
    Text(text = text, color = MaterialTheme.colorScheme.secondary)
}