package com.mandi.ui.base.compose

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import com.mandi.R
import com.mandi.ui.theme.GreenDark

@Composable
fun IndeterminateCircularProgressBarWithDrawable(
   imageVector: ImageVector = Icons.Filled.Refresh,
    tint: Color = GreenDark,
    contentDescription: String = stringResource(id = R.string.loading),
    modifier: Modifier = Modifier
) {
    val infiniteTransitionAngle = rememberInfiniteTransition()
    val angle by infiniteTransitionAngle.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 500
            },
            repeatMode = RepeatMode.Restart
        )
    )

    Icon(imageVector = imageVector, tint = tint, contentDescription = contentDescription,
        modifier = modifier.then(
            Modifier
                .rotate(angle)
                .semantics {
                    liveRegion = LiveRegionMode.Polite
                })
    )
}