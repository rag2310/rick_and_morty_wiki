package com.rago.rickandmortiwiki.presentation.screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rago.rickandmortiwiki.R
import com.rago.rickandmortiwiki.presentation.ui.composables.AnimationSplash
import com.rago.rickandmortiwiki.presentation.ui.composables.DesignSplashScreen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavHome: () -> Unit
) {
    SplashContent(onNavHome)
}

@Composable
fun SplashContent(
    onNavHome: () -> Unit,
) {

    val success = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit) {
        delay(1500)
        success.value = true
    }

    val scaleAnimation: Animatable<Float, AnimationVector1D> =
        remember {
            Animatable(initialValue = 0f)
        }

    AnimationSplash(
        scaleAnimation = scaleAnimation,
        durationMillisAnimation = 1500,
        success = success.value,
        onNav = onNavHome
    )

    Scaffold(
        Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            DesignSplashScreen(
                modifier = Modifier
                    .statusBarsPadding()
                    .navigationBarsPadding(),
                imagePainter = painterResource(id = R.drawable.logo_app),
                scaleAnimation = scaleAnimation
            )
        }
    }
}