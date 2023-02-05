package com.mandi.ui.sellingcomplete

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.mandi.R
import com.mandi.ui.base.compose.PrimaryButton
import com.mandi.ui.base.compose.Text
import com.mandi.ui.navigation.NavigationActions
import com.mandi.ui.navigation.SellerInventoryInfo
import com.mandi.ui.navigation.SellingScreenInfo
import com.mandi.ui.theme.CharcoalDark
import com.mandi.ui.theme.typography

@Composable
fun SellingComplete(
    sellingCompleteViewModel: SellingCompleteViewModel,
    navigationActions: NavigationActions,
    sellerInventoryInfo: SellerInventoryInfo?
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = dimensionResource(id = R.dimen.margin))) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier
            .fillMaxSize()
            .weight(1f)
            .padding(top = dimensionResource(id = R.dimen.margin),
                start = dimensionResource(id = R.dimen.margin_2x),
                end = dimensionResource(id = R.dimen.margin_2x))
        ) {

            Image(
                painter = painterResource(id = R.drawable.fruit_box),
                contentDescription = null,
                modifier = Modifier
                    .weight(2f)
                    .padding(top = dimensionResource(id = R.dimen.margin_3x))
            )
            sellerInventoryInfo?.let {
                Text(
                    text = stringResource(id = R.string.thank_you_x_for_selling_your_quality_produce, it.sellerName),
                    textAlign = TextAlign.Center,
                    textStyle = typography.headlineLarge,
                    modifier = Modifier
                        .padding(top = dimensionResource(id = R.dimen.margin_3x))
                        .weight(1f),
                    isBold = true
                )
                Text(
                    text = it.inventoryDescription,
                    textAlign = TextAlign.Center,
                    textStyle = typography.bodyMedium,
                    color = CharcoalDark,
                    isBold = true,
                    modifier = Modifier
                        .padding(top = dimensionResource(id = R.dimen.margin_1_5x))
                        .weight(1f)
                )
                Spacer(modifier = Modifier.weight(2f))
            }
        }
        PrimaryButton(text = stringResource(id = R.string.sell_more), onClick = {navigationActions.navToSellingScreen(null)})
    }
}