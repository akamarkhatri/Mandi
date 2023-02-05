package com.mandi.ui.selling

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.internal.enableLiveLiterals
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mandi.R
import com.mandi.ui.base.compose.*
import com.mandi.ui.navigation.NavigationActions
import com.mandi.ui.navigation.SellerInventoryInfo
import com.mandi.ui.search.SearchContentInfo
import com.mandi.ui.search.SearchContentType
import com.mandi.ui.theme.*
import com.mandi.util.numberFormat
import java.util.*

@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalComposeUiApi::class)
@Composable
fun SellingScreen(sellingViewModel: SellingViewModel, navigationActions: NavigationActions) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .padding(
            horizontal = dimensionResource(id = R.dimen.margin_3x),
            vertical = dimensionResource(id = R.dimen.margin_2x)
        )
    ) {
        val state by sellingViewModel.uiState.collectAsStateWithLifecycle()

        Text(text = stringResource(id = R.string.app_name),
            textStyle = typography.headlineMedium,
            isBold = true)
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                RenderScreenContent(state, sellingViewModel.event, Modifier.weight(1f), navigationActions)
                RenderBottomContent(state, sellingViewModel.event, navigationActions)
            }
        }
    }
}

@Composable
fun RenderBottomContent(
    state: SellingScreenState,
    event: (SellingScreenEvent) -> Unit,
    navigationActions: NavigationActions,
) {
    val grossValueText = state.grossValue?.let {
        numberFormat.format(it)
    } ?: "----"
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(
            top = dimensionResource(id = R.dimen.margin_3x),
            bottom = dimensionResource(id = R.dimen.margin_2x)
        )) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(id = R.string.gross_price),
                textStyle = typography.bodyLarge,
                isBold = true,
                modifier = Modifier.weight(1f))

            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(dimensionResource(id = R.dimen.margin_2x)))
            } else {
                Text(text = grossValueText, textStyle = typography.bodyLarge, isBold = true)
            }
        }


        val loyalityIndexValue = state.sellerInfo?.sellerRegistrationInfo?.getLoyalityIndexValue()?.let { index->
            state.grossValue?.let { stringResource(id = R.string.applied_loyality_index, index) }
        } ?: "--"
        Text(text = loyalityIndexValue,
            textStyle = typography.bodySmall,
            color = GreenDark)

        Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.margin)))
        val context = LocalContext.current
        PrimaryButton(
            text = stringResource(id = R.string.sell_my_produce),
            onClick = {
                navigationActions.navToSellingComplete(
                    SellerInventoryInfo(
                        state.sellerInfo?.name.orEmpty(),
                        context.getString(
                            R.string.please_ensure_you_received_x_y_for_z_measure_of_your_produce,
                            grossValueText,
                            numberFormat.currency?.getSymbol(Locale.US).orEmpty(),
                            state.sellingCommodityWt.text.toFloat(),
                            context.getString(
                                state.selectedCommodityInfo?.commodityDetail?.commodityMeasurementType?.getMesurementTypeNameResId()
                                    ?: R.string.tonnes
                            )
                        )
                    )
                )
            },
            enabled = state.grossValue != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.margin_3x))
        )

    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RenderScreenContent(
    state: SellingScreenState,
    event: (SellingScreenEvent) -> Unit,
    modifier: Modifier = Modifier,
    navigationActions: NavigationActions,
) {
    val scrollState = rememberScrollState()
    Column(modifier = modifier.verticalScroll(scrollState)) {
        RenderScreenContentItem(title = stringResource(id = R.string.seller_name),
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.margin_2x))) {
            TextFieldNormal(
                textFieldValue = TextFieldValue(state.sellerInfo?.name.orEmpty()),
                onValueChange = {
                },
                enabled = false,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navigationActions.navToSearchContentScreen(
                            SearchContentInfo.Seller(
                                SearchContentType.SELLER_BY_NAME
                            )
                        )
                    },
                textFieldColorInfo = TextFieldColorInfo(disabledTextColor = CharcoalDark)
            )
        }

        RenderScreenContentItem(title = stringResource(id = R.string.loyality_card_identifier)) {
            TextFieldNormal(
                textFieldValue = TextFieldValue(state.sellerInfo?.sellerRegistrationInfo?.getCardId().orEmpty()),
                onValueChange = {

                },
                maxLines = 1,
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navigationActions.navToSearchContentScreen(
                            SearchContentInfo.Seller(
                                SearchContentType.SELLER_BY_LC_ID
                            )
                        )
                    },
                textFieldColorInfo = TextFieldColorInfo(disabledTextColor = CharcoalDark)
            )
        }

        RenderScreenContentItem(title = stringResource(id = R.string.village)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .fillMaxWidth()
                .clickable {
//                    event(SellingScreenEvent.UpdateVillageBottomSheetStatus(true))
                    navigationActions.navToSearchContentScreen(
                        SearchContentInfo.Village(
                            SearchContentType.VILLAGE
                        )
                    )
                }) {
                Text(text = state.selectedVillageInfo?.name.orEmpty(),
                    textStyle = typography.bodyLarge,
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = dimensionResource(id = R.dimen.margin_2x)))
                Icon(imageVector = Icons.Filled.KeyboardArrowDown, tint = GreenDark, contentDescription = null)
            }
        }

        RenderScreenContentItem(title = stringResource(id = R.string.commodity)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = state.selectedVillageInfo != null) {
//                    event(SellingScreenEvent.UpdateCommodityBottomSheetStatus(true))
                    navigationActions.navToSearchContentScreen(
                        SearchContentInfo.Commodity(
                            SearchContentType.COMMODITY,
                            state.selectedVillageInfo?.id.orEmpty()
                        )
                    )
                }) {
                Text(text = state.selectedCommodityInfo?.commodityDetail?.name.orEmpty(),
                    textStyle = typography.bodyLarge,
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = dimensionResource(id = R.dimen.margin_2x)))
                Icon(imageVector = Icons.Filled.KeyboardArrowDown, tint = GreenDark, contentDescription = null)
            }
        }

        RenderScreenContentItem(title = stringResource(id = R.string.gross_weight)) {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()) {
                TextFieldNormal(textFieldValue = state.sellingCommodityWt,
                    maxCharacters = 5,
                    maxLines = 1,
                    onValueChange = {
                        event(SellingScreenEvent.OnGrossWtUpdate(it))
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    enabled = state.selectedCommodityInfo != null
                )
                state.selectedCommodityInfo?.let {
                    Text(text = stringResource(id = it.commodityDetail.commodityMeasurementType.getMesurementTypeNameResId()),
                        textStyle = typography.bodyLarge,
                        isBold = true)
                }

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
            .padding(
                horizontal = dimensionResource(id = R.dimen.margin_2x),
                vertical = dimensionResource(
                    id = R.dimen.margin_3x
                )
            )) {
            Text(text = title,
                textStyle = typography.bodyLarge,
                color = Neutral50)
            Column(verticalArrangement = Arrangement.Center, modifier = Modifier
                .padding(top = dimensionResource(id = R.dimen.margin_2x))
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.margin))
                )
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(id = R.dimen.margin),
                    end = dimensionResource(id = R.dimen.margin),
                    bottom = dimensionResource(id = R.dimen.margin)
                )
            ) {
                content(this)
            }
        }
    }
}

@Preview
@Composable
fun PreviewRenderScreenContent() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(dimensionResource(id = R.dimen.margin_2x))) {
        RenderScreenContentItem(title = "Seller Name") {
            TextFieldNormal(textFieldValue = TextFieldValue("Amar"),
                onValueChange = { },
                Modifier.fillMaxWidth())
        }

        RenderScreenContentItem(title = "Loyality Card Identifier",
            modifier = Modifier.padding(top = dimensionResource(
                id = R.dimen.margin_2x))) {
            TextFieldNormal(textFieldValue = TextFieldValue("S100"), onValueChange = { },
                Modifier
                    .fillMaxWidth())
        }
    }
}