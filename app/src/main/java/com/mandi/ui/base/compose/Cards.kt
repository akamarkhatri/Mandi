package com.mandi.ui.base.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.mandi.R
import com.mandi.ui.theme.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Card(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    shape: RoundedCornerShape = RoundedCornerShape(dimensionResource(id = R.dimen.margin)),
    containerColor: Color = GreenLight,
    cardElevation: CardElevation = CardDefaults.cardElevation(),
    content: @Composable ColumnScope.() -> Unit
) {
    if (onClick != null) {
        androidx.compose.material3.Card(
            onClick = onClick,
            modifier = modifier,
            shape = shape,
            colors = getCardColors(containerColor),
            elevation = cardElevation,
            content = content
        )
    } else {
        androidx.compose.material3.Card(
            modifier = modifier,
            shape = shape,
            colors = getCardColors(containerColor),
            elevation = cardElevation,
            content = content
        )
    }
}

@Preview
@Composable
fun PreviewCard() {
    Card(modifier = Modifier
        .fillMaxWidth()) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.margin_2x))
        ) {

            Text(text = "Sellar Name", textStyle = typography.bodyLarge, color = Neutral50)

            TextFieldNormal(textFieldValue = TextFieldValue("Amar"), onValueChange = { },
                Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(
                        id = R.dimen.margin_2x)))
        }
    }
}