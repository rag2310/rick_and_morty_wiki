package com.rago.rickandmortiwiki

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.rago.rickandmortiwiki.presentation.screen.CharactersScreen
import com.rago.rickandmortiwiki.presentation.ui.theme.RickAndMortiWikiTheme
import com.rago.rickandmortiwiki.presentation.uistate.CharactersUIState
import com.rago.rickandmortiwiki.presentation.viewmodel.CharactersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortiWikiTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding).background(Color.White)
                    ) {
                        val charactersViewModel: CharactersViewModel = hiltViewModel()
                        val charactersUIState: CharactersUIState by charactersViewModel.uiState.collectAsState()
                        CharactersScreen(charactersUIState)
                    }
                }
            }
        }
    }
}