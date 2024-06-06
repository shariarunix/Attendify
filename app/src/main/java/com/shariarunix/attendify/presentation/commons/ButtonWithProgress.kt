package com.shariarunix.attendify.presentation.commons

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shariarunix.attendify.utils.CORNER_SIZE

@Composable
fun ButtonWithProgress(
    onClick: () -> Unit,
    showProgress: Boolean,
    buttonText: String,
    modifier: Modifier = Modifier,
    buttonShape: RoundedCornerShape = RoundedCornerShape(CORNER_SIZE),
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(),
    progressColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    Button(
        onClick = onClick,
        shape = buttonShape,
        colors = buttonColors,
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        AnimatedContent(
            targetState = showProgress,
            label = "ButtonWithProgress",
            transitionSpec = {
                (slideInHorizontally(
                    animationSpec = tween(200),
                    initialOffsetX = { it }
                ) + fadeIn(
                    animationSpec = tween(150, delayMillis = 75),
                    initialAlpha = 0f
                )).togetherWith(
                    (slideOutHorizontally(
                        animationSpec = tween(200),
                        targetOffsetX = { -it }
                    ) + fadeOut(
                        animationSpec = tween(75),
                        targetAlpha = 1f
                    ))
                )
            }
        ) { targetState ->
            when (targetState) {
                true -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(32.dp),
                            color = progressColor,
                            trackColor = progressColor.copy(alpha = 0.3F)
                        )
                    }
                }

                false -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = buttonText,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                }
            }
        }
    }
}