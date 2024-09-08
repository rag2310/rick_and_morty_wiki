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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rago.rickandmortiwiki.data.utils.Constants
import com.rago.rickandmortiwiki.data.utils.Constants.HOME_SCREEN
import com.rago.rickandmortiwiki.data.utils.Constants.SPLASH_SCREEN
import com.rago.rickandmortiwiki.presentation.screen.CharactersScreen
import com.rago.rickandmortiwiki.presentation.screen.SplashScreen
import com.rago.rickandmortiwiki.presentation.ui.theme.RickAndMortiWikiTheme
import com.rago.rickandmortiwiki.presentation.uistate.CharactersUIState
import com.rago.rickandmortiwiki.presentation.viewmodel.CharactersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            RickAndMortiWikiTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .background(MaterialTheme.colorScheme.tertiary)
                    ) {
                        NavigationScreen(navController)
                    }
                }
            }
        }
    }

    @Composable
    private fun NavigationScreen(navController: NavHostController) {
        NavHost(navController = navController, startDestination = SPLASH_SCREEN) {
            composable(SPLASH_SCREEN) {
                SplashScreen {
                    navController.navigateWithPopUp(HOME_SCREEN, SPLASH_SCREEN)
                }
            }
            composable(HOME_SCREEN) {
                val charactersViewModel: CharactersViewModel = hiltViewModel()
                val charactersUIState: CharactersUIState by charactersViewModel.uiState.collectAsState()
                CharactersScreen(charactersUIState)
            }
        }
    }

    fun NavHostController.navigateWithPopUp(
        toRoute: String,
        fromRoute: String
    ) {
        this.navigate(toRoute) {
            popUpTo(fromRoute) {
                inclusive = true
            }
        }
    }
}

