package com.mandi.ui.selling

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.mandi.R
import com.mandi.ui.base.compose.Card
import com.mandi.ui.base.compose.PrimaryButton
import com.mandi.ui.base.compose.Text
import com.mandi.ui.base.compose.TextFieldNormal
import com.mandi.ui.navigation.NavigationActions
import com.mandi.ui.navigation.SellerInventoryInfo
import com.mandi.ui.theme.GreenDark
import com.mandi.ui.theme.GreenLight
import com.mandi.ui.theme.Neutral50
import com.mandi.ui.theme.typography

@Composable
fun SellingScreen(sellingViewModel: SellingViewModel, navigationActions: NavigationActions) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .padding(horizontal = dimensionResource(id = R.dimen.margin_3x),
            vertical = dimensionResource(id = R.dimen.margin_2x))
    ) {
        Text(text = stringResource(id = R.string.app_name), textStyle = typography.headlineMedium, isBold = true)
        RenderScreenContent(Modifier.weight(1f))
        RenderBottomContent(navigationActions)
    }
}

@Composable
fun RenderBottomContent(navigationActions: NavigationActions) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = dimensionResource(id = R.dimen.margin_3x),
            bottom = dimensionResource(id = R.dimen.margin_2x))) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(id = R.string.gross_price), textStyle = typography.bodyLarge, isBold = true, modifier = Modifier.weight(1f))
            Text(text = stringResource(id = R.string.x_inr, "20,000.23"), textStyle = typography.bodyLarge, isBold = true)
        }
        Text(text = stringResource(id = R.string.applied_loyality_index, 1.1233f), textStyle = typography.bodySmall, color = GreenDark)
        Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.margin)))
        PrimaryButton(
            text = stringResource(id = R.string.sell_my_produce),
            onClick = {
                navigationActions.navToSellingComplete(SellerInventoryInfo("Amar Khatri", "thanks you will receive 20000"))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.margin_3x))
        )
        
    }
}

@Composable
fun RenderScreenContent(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    Column(modifier = modifier.verticalScroll(scrollState)) {
        RenderScreenContentItem(title = stringResource(id = R.string.seller_name),modifier = Modifier.padding(top = dimensionResource(id = R.dimen.margin_2x))) {
            TextFieldNormal(textFieldValue = TextFieldValue("Amar"), onValueChange = { }, Modifier.fillMaxWidth())
        }

        RenderScreenContentItem(title = stringResource(id = R.string.loyality_card_identifier)) {
            TextFieldNormal(textFieldValue = TextFieldValue("S100"), onValueChange = { }, Modifier.fillMaxWidth())
        }

        RenderScreenContentItem(title = stringResource(id = R.string.loyality_card_identifier)) {
            TextFieldNormal(textFieldValue = TextFieldValue("Village"), onValueChange = { }, Modifier.fillMaxWidth())
        }
        RenderScreenContentItem(title = stringResource(id = R.string.gross_weight)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                TextFieldNormal(textFieldValue = TextFieldValue("Weight"), onValueChange = { },
                    Modifier
                        .fillMaxWidth()
                        .weight(1f))
                Text(text = stringResource(id = R.string.tonnes), textStyle = typography.bodyLarge, isBold = true)
            }
        }
    }
}

@Composable
private fun RenderScreenContentItem(
    title: String,
    modifier: Modifier = Modifier.padding(top = dimensionResource(
        id = R.dimen.margin_4x)),
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(modifier = modifier) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_2x),
                vertical = dimensionResource(
                    id = R.dimen.margin_3x))) {
            Text(text = title,
                textStyle = typography.bodyLarge,
                color = Neutral50)
            Column(verticalArrangement = Arrangement.Center,modifier = Modifier
                .padding(top = dimensionResource(id = R.dimen.margin_2x))
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.margin))
                )
                .fillMaxWidth()
                .padding(start = dimensionResource(id = R.dimen.margin),
                    end = dimensionResource(id = R.dimen.margin),
                    bottom = dimensionResource(id = R.dimen.margin))
            ) {
                content(this)
            }
        }
    }
}

@Preview
@Composable
fun PreviewRenderScreenContent(){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(dimensionResource(id = R.dimen.margin_2x))) {
        RenderScreenContentItem(title = "Seller Name") {
            TextFieldNormal(textFieldValue = TextFieldValue("Amar"), onValueChange = { }, Modifier.fillMaxWidth())
        }

        RenderScreenContentItem(title = "Loyality Card Identifier", modifier = Modifier.padding(top = dimensionResource(
            id = R.dimen.margin_2x))) {
            TextFieldNormal(textFieldValue = TextFieldValue("S100"), onValueChange = { },
                Modifier
                    .fillMaxWidth())
        }
    }
}