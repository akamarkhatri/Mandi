package com.mandi.ui.theme

import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val CharcoalDark = Color(0xFF666666)
val CharcoalLight = Color(0xFF333333)
val GreenLight = Color(0x4D0DC714)
val GreenDark = Color(0xFF0CA812)
val Grenadier = Color(0xFFCD3C3C)
val Neutral50 = Color(0xFF808080)


@Composable
fun getPrimaryButtonColors(isDestructive: Boolean = false) = ButtonDefaults.buttonColors(
    containerColor = if (isDestructive) Grenadier else GreenDark,
    contentColor = Color.White,
    disabledContentColor = Color.White,
    disabledContainerColor = Neutral50
)