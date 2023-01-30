package com.mandi.ui.theme

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val CharcoalDark = Color(0xFF666666)
val CharcoalLight = Color(0xFF333333)
val GreenLight = Color(0x330DC714)
val GreenDark = Color(0xFF0CA812)
val Grenadier = Color(0xFFCD3C3C)
val Neutral50 = Color(0xFF808080)
val SemiTransparent = Color(0x50000000)


@Composable
fun getPrimaryButtonColors(isDestructive: Boolean = false) = ButtonDefaults.buttonColors(
    containerColor = if (isDestructive) Grenadier else GreenDark,
    contentColor = Color.White,
    disabledContentColor = Color.White,
    disabledContainerColor = Neutral50
)

@Composable
fun getCardColors(containerColor: Color = GreenLight) = CardDefaults.cardColors(
    containerColor = containerColor
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun getTextFieldColors() = TextFieldDefaults.textFieldColors(
    textColor = CharcoalDark,
    disabledTextColor = CharcoalLight,
    containerColor = Color.White,
    cursorColor = GreenDark,
    errorCursorColor = Grenadier,
    focusedLabelColor = GreenDark,
    unfocusedLabelColor = CharcoalLight,
    errorLabelColor = Grenadier,
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    disabledIndicatorColor = Color.Transparent,
    errorIndicatorColor = Grenadier
)