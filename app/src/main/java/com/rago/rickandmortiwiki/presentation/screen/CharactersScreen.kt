package com.rago.rickandmortiwiki.presentation.screen

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
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
import com.rago.rickandmortiwiki.data.model.Location
import com.rago.rickandmortiwiki.data.model.Origin
import com.rago.rickandmortiwiki.data.model.Result
import com.rago.rickandmortiwiki.data.model.seedResult
import com.rago.rickandmortiwiki.presentation.ui.composables.ErrorDialog
import com.rago.rickandmortiwiki.presentation.ui.composables.LoadingDialog
import com.rago.rickandmortiwiki.presentation.uistate.CharactersUIState

@Composable
fun CharactersScreen(charactersUIState: CharactersUIState) {

    LoadingDialog(showDialog = charactersUIState.loading)

    ErrorDialog(errorRes = charactersUIState.errorRes) {
        charactersUIState.onClearError()
    }

    ErrorDialog(errorMsg = charactersUIState.error) {
        charactersUIState.onClearError()
    }

    LaunchedEffect(key1 = Unit) {
        charactersUIState.getCharacters()
    }
    CharactersScreenContent(charactersUIState)
}

@Preview(showBackground = true)
@Composable
private fun CharactersScreenContent(charactersUIState: CharactersUIState = CharactersUIState(
    characters = listOf(seedResult)
)) {
    Crossfade(targetState = charactersUIState.characters, label = "list_characters") {
        if (it.isEmpty()) {
            CharactersScreenEmptyData()
        } else {
            LazyColumn(Modifier.fillMaxSize()) {
                items(charactersUIState.characters) { result ->
                    ItemCharacter(result)
                }
                if (charactersUIState.characters.isNotEmpty()) {
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

@Preview(showBackground = true)
@Composable
private fun CharactersScreenEmptyData() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Filled.Inbox, contentDescription = null, modifier = Modifier.size(64.dp))
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.not_data),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemCharacter(
    result: Result = seedResult
) {

    val statusColor = when (result.status?.lowercase() ?: "") {
        "alive" -> {
            Color.Green
        }

        "dead" -> {
            Color.Red
        }

        else -> Color.Gray
    }
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 6.dp), colors = CardDefaults.elevatedCardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)) {
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
                Text(text = result.name ?: "", style = MaterialTheme.typography.titleSmall)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(statusColor)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = result.status ?: "")
                    Text(text = " - ")
                    Text(text = result.species ?: "")
                }

                Text(
                    modifier = Modifier.padding(top = 6.dp),
                    text = stringResource(id = R.string.last_known_location),
                    style = MaterialTheme.typography.labelMedium, color = Color.Gray
                )
                Text(
                    text = result.location?.name ?: "",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    modifier = Modifier.padding(top = 6.dp),
                    text = stringResource(id = R.string.first_seen_in),
                    style = MaterialTheme.typography.labelMedium, color = Color.Gray
                )
                Text(text = result.origin?.name ?: "", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}