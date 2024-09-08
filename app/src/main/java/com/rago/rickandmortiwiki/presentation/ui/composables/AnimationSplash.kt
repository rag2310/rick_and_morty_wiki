package com.rago.rickandmortiwiki.presentation.ui.composables

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationResult
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun AnimationSplash(
    scaleAnimation: Animatable<Float, AnimationVector1D>,
    durationMillisAnimation: Int,
    success: Boolean,
    onNav: () -> Unit
) {
    AnimationSplashContent(scaleAnimation, durationMillisAnimation, success, onNav)
}

@Composable
fun AnimationSplashContent(
    scaleAnimation: Animatable<Float, AnimationVector1D>,
    durationMillisAnimation: Int,
    success: Boolean,
    onNav: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        scaleAnimation.animateTo(
            targetValue = 0.5f,
            animationSpec = tween(
                durationMillis = durationMillisAnimation,
                easing = {
                    OvershootInterpolator(3f).getInterpolation(it)
                }
            )
        )
    }
    LaunchedEffect(key1 = success) {
        if (success) {
            onNav()
        }
    }
}
