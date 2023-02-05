package com.mandi.ui.base.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import com.mandi.R
import com.mandi.ui.theme.Neutral50
import com.mandi.ui.theme.getPrimaryButtonColors
import com.mandi.ui.theme.typography

@Composable
fun getButtonShape() = RoundedCornerShape(dimensionResource(R.dimen.button_corner_radius))

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(
            // This is the normal padding for a large bottom button
            start = dimensionResource(R.dimen.margin_2x),
            end = dimensionResource(R.dimen.margin_2x),
            top = dimensionResource(R.dimen.margin_2x),
            bottom = dimensionResource(R.dimen.margin_3x)
        ),
    isDestructive: Boolean = false,
    allCaps: Boolean = false,
    testTag: String = "PrimaryButton_$text",
) {
    Button(
        modifier = modifier.then(
            Modifier
                .height(dimensionResource(R.dimen.button_height_large))
                .testTag(testTag)
        ),
        enabled = enabled,
        onClick = onClick,
        colors = getPrimaryButtonColors(isDestructive),
        shape = getButtonShape()
    ) {
        Text(
            text = text,
            textStyle = typography.bodyLarge,
            textAlign = TextAlign.Center,
            allCaps = allCaps,
            isBold = true,
            color = if (enabled) Color.White else Neutral50
        )
    }
}

@Composable
fun LargePrimaryButtonInternals(text: String) {
    TODO("Not yet implemented")
}
