package com.mandi.ui.base.compose

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.mandi.ui.theme.CharcoalDark
import com.mandi.ui.theme.typography

@Composable
fun Text(
    text: String,
    textAlign: TextAlign? = null,
    isBold: Boolean = false,
    textStyle: TextStyle = typography.headlineLarge,
    color: Color = Color.Black,
    allCaps: Boolean = false,
    modifier: Modifier = Modifier
) {
    val fontWeight = if (isBold)
        FontWeight.Bold
    else
        textStyle.fontWeight
    Text(text = text.let { if (allCaps) text.uppercase() else text },
        style = textStyle.copy(
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign
        ),
        modifier = modifier
    )
}