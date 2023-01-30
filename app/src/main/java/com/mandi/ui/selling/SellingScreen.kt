package com.mandi.ui.selling

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mandi.R
import com.mandi.model.CommodityMeasurementType
import com.mandi.ui.base.compose.*
import com.mandi.ui.navigation.NavigationActions
import com.mandi.ui.navigation.SellerInventoryInfo
import com.mandi.ui.theme.GreenDark
import com.mandi.ui.theme.Neutral50
import com.mandi.ui.theme.typography
import com.mandi.util.numberFormat
import java.util.*

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun SellingScreen(sellingViewModel: SellingViewModel, navigationActions: NavigationActions) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .padding(horizontal = dimensionResource(id = R.dimen.margin_3x),
            vertical = dimensionResource(id = R.dimen.margin_2x))
    ) {
        val state by sellingViewModel.uiState.collectAsStateWithLifecycle()

        Text(text = stringResource(id = R.string.app_name),
            textStyle = typography.headlineMedium,
            isBold = true)
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxSize()) {
                RenderScreenContent(state, sellingViewModel.event, Modifier.weight(1f))
                RenderBottomContent(state, sellingViewModel.event, navigationActions)
            }
            if (state.isLoading) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = false) {}) {
                    CircularProgressIndicator(color = GreenDark,
                        modifier = Modifier.align(Alignment.Center))
                }
            }
            if (state.moreThanOneSellerFound) {
                RenderSellersBottomSheet(state, sellingViewModel.event)
            }

            if (state.canShowVillageBottomSheet) {
                RenderVillageBottomSheet(state, sellingViewModel.event)
            }

            if (state.canShowCommodityBottomSheet) {
                RenderCommodityBottomSheet(state, sellingViewModel.event)
            }
        }
    }
}

@Composable
private fun RenderCommodityBottomSheet(
    state: SellingScreenState,
    event: (SellingScreenEvent) -> Unit,
) {
    BottomSheet(outSideOnClick = { event(SellingScreenEvent.UpdateCommodityBottomSheetStatus(false)) }) {
        Text(
            text = stringResource(id = R.string.select_commodity_to_sell),
            textStyle = typography.headlineMedium,
            isBold = true,
            modifier = Modifier.padding(start = dimensionResource(id = R.dimen.margin_2x),
                top = dimensionResource(
                    id = R.dimen.margin),
                end = dimensionResource(id = R.dimen.margin_2x))
        )

        LazyColumn(modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.margin_2x),
            vertical = dimensionResource(
                id = R.dimen.margin_2x))) {
            state.selectedVillageInfo?.sellingCommoditiesList?.forEach { sellingCommodityInfo ->
                item {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            event(SellingScreenEvent.UpdateCommodityBottomSheetStatus(false,
                                sellingCommodityInfo))
                        }) {
                        val commodityMeasurementType =
                            sellingCommodityInfo.commodityDetail.commodityMeasurementType
                        val unitResId =
                            if (commodityMeasurementType == CommodityMeasurementType.Killogram) {
                                R.string.kg
                            } else {
                                commodityMeasurementType.getMesurementTypeNameResId()
                            }
                        val label =
                            stringResource(id = R.string.x_y_per_z_unit, formatArgs = arrayOf(
                                sellingCommodityInfo.commodityDetail.name,
                                sellingCommodityInfo.pricePerMeasurementType,
                                stringResource(id = unitResId)
                            ))
                        Text(text = label,
                            textStyle = typography.bodyLarge,
                            modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_2x)))
                        Divider(color = Neutral50)
                    }
                }
            }
        }
    }
}

@Composable
fun RenderVillageBottomSheet(state: SellingScreenState, event: (SellingScreenEvent) -> Unit) {

    BottomSheet(outSideOnClick = { event(SellingScreenEvent.UpdateVillageBottomSheetStatus(false)) }) {
        Text(
            text = stringResource(id = R.string.select_village),
            textStyle = typography.headlineMedium,
            isBold = true,
            modifier = Modifier.padding(start = dimensionResource(id = R.dimen.margin_2x),
                top = dimensionResource(
                    id = R.dimen.margin),
                end = dimensionResource(id = R.dimen.margin_2x))
        )

        LazyColumn(modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.margin_2x),
            vertical = dimensionResource(
                id = R.dimen.margin_2x))) {
            state.allVillageList.forEach { villageInfo ->
                item {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            event(SellingScreenEvent.UpdateVillageBottomSheetStatus(false,
                                villageInfo))
                        }) {
                        Text(text = "${villageInfo.name} (${villageInfo.postalCode})",
                            textStyle = typography.bodyLarge,
                            modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_2x)))
                        Divider(color = Neutral50)
                    }
                }
            }
        }
    }
}

@Composable
fun RenderSellersBottomSheet(state: SellingScreenState, event: (SellingScreenEvent) -> Unit) {
    BottomSheet(outSideOnClick = { event(SellingScreenEvent.UpdateVillageBottomSheetStatus(false)) }) {
        Text(
            text = stringResource(id = R.string.select_seller),
            textStyle = typography.headlineMedium,
            isBold = true,
            modifier = Modifier.padding(start = dimensionResource(id = R.dimen.margin_2x),
                top = dimensionResource(
                    id = R.dimen.margin),
                end = dimensionResource(id = R.dimen.margin_2x))
        )

        LazyColumn(modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.margin_2x),
            vertical = dimensionResource(
                id = R.dimen.margin_2x))) {
            state.sellerList.forEach { sellerInfo ->
                item {

                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            event(SellingScreenEvent.UpdateSellersBottomSheetStatus(false,
                                sellerInfo))
                        }) {
                        Text(text = "${sellerInfo.name} ${sellerInfo.getLoyalityCardId()}",
                            textStyle = typography.bodyLarge,
                            modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_2x)))
                        Divider(color = Neutral50)
                    }
                }
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
        .padding(top = dimensionResource(id = R.dimen.margin_3x),
            bottom = dimensionResource(id = R.dimen.margin_2x))) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(id = R.string.gross_price),
                textStyle = typography.bodyLarge,
                isBold = true,
                modifier = Modifier.weight(1f))
            Text(text = grossValueText,
                textStyle = typography.bodyLarge,
                isBold = true)
        }
        state.grossValue?.let {
            val loyalityIndex = state.sellerInfo?.sellerRegistrationInfo?.loyalityIndex ?: 0f
            Text(text = stringResource(id = R.string.applied_loyality_index, loyalityIndex),
                textStyle = typography.bodySmall,
                color = GreenDark)
        }
        Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.margin)))
        val context = LocalContext.current
        PrimaryButton(
            text = if (state.grossValue == null) stringResource(id = R.string.calculate_gross_value) else stringResource(id = R.string.sell_my_produce),
            onClick = {
                if (state.grossValue == null) {
                        event(SellingScreenEvent.CalculateGrossValue)
                } else {
                    navigationActions.navToSellingComplete(SellerInventoryInfo(state.sellerInfo?.name.orEmpty(),
                         context.getString(
                             R.string.please_ensure_you_received_x_y_for_z_measure_of_your_produce,
                             grossValueText,
                             numberFormat.currency?.getSymbol(Locale.US).orEmpty(),
                             state.sellingCommodityWt.text.toFloat(),
                             context.getString(state.selectedCommodityInfo?.commodityDetail?.commodityMeasurementType?.getMesurementTypeNameResId()
                                 ?: R.string.tonnes)
                         )
                    ))
                }

            },
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
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollState = rememberScrollState()
    Column(modifier = modifier.verticalScroll(scrollState)) {
        RenderScreenContentItem(title = stringResource(id = R.string.seller_name),
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.margin_2x))) {
            TextFieldNormal(
                textFieldValue = state.sellerNameValue,
                onValueChange = {
                    event(SellingScreenEvent.OnTextFieldValueChange(it,
                        TextFieldValueType.Seller))
                },
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions {
                    keyboardController?.hide()
                    event(SellingScreenEvent.FetchSeller(TextFieldValueType.Seller))
                }
            )
        }

        RenderScreenContentItem(title = stringResource(id = R.string.loyality_card_identifier)) {
            TextFieldNormal(
                textFieldValue = state.sellerLoyalityCardValue,
                onValueChange = {
                    event(SellingScreenEvent.OnTextFieldValueChange(it, TextFieldValueType.LoyalityCard))
                },
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions {
                    keyboardController?.hide()
                    event(SellingScreenEvent.FetchSeller(TextFieldValueType.LoyalityCard))
                }
            )
        }

        RenderScreenContentItem(title = stringResource(id = R.string.village)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    event(SellingScreenEvent.UpdateVillageBottomSheetStatus(true))
                }) {
                Text(text = state.selectedVillageInfo?.name.orEmpty(),
                    textStyle = typography.bodyLarge,
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = dimensionResource(id = R.dimen.margin_2x)))
                Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = null)
            }
        }

        RenderScreenContentItem(title = stringResource(id = R.string.commodity)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = state.selectedVillageInfo != null) {
                    event(SellingScreenEvent.UpdateCommodityBottomSheetStatus(true))
                }) {
                Text(text = state.selectedCommodityInfo?.commodityDetail?.name.orEmpty(),
                    textStyle = typography.bodyLarge,
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = dimensionResource(id = R.dimen.margin_2x)))
                Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = null)
            }
        }

        RenderScreenContentItem(title = stringResource(id = R.string.gross_weight)) {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()) {
                TextFieldNormal(textFieldValue = state.sellingCommodityWt,
                    maxCharacters = 5,
                    maxLines = 1,
                    onValueChange = {
                        event(SellingScreenEvent.OnTextFieldValueChange(it,
                            TextFieldValueType.GrossWt))
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
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
            .padding(horizontal = dimensionResource(id = R.dimen.margin_2x),
                vertical = dimensionResource(
                    id = R.dimen.margin_3x))) {
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