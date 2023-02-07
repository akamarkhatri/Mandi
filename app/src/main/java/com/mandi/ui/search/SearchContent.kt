package com.mandi.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mandi.R
import com.mandi.model.CommodityMeasurementType
import com.mandi.model.Seller
import com.mandi.model.SellingCommodityInfo
import com.mandi.model.VillageInfo
import com.mandi.ui.base.compose.*
import com.mandi.ui.navigation.NavigationActions
import com.mandi.ui.navigation.SellingScreenInfo
import com.mandi.ui.theme.Neutral50
import com.mandi.ui.theme.typography


@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchContent(searchContentViewModel: SearchContentViewModel, navigationActions: NavigationActions) {
    val state by searchContentViewModel.searchState.collectAsStateWithLifecycle()
    val focusRequester = remember { FocusRequester() }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = dimensionResource(id = R.dimen.margin_2x))
    ) {
        state.searchContentInfo?.getType()?.let {
            Card {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = dimensionResource(id = R.dimen.margin_2x),
                        vertical = dimensionResource(
                            id = R.dimen.margin_3x
                        )
                    )) {
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(
                                id = R.string.search_content,
                                stringResource(id = it.titleResId)
                            ),
                            textStyle = typography.headlineLarge,
                            isBold = true,
                            modifier = Modifier.weight(1f)
                        )
                        if (state.isLoading) {
                            CircularProgressIndicator(modifier = Modifier.size(dimensionResource(id = R.dimen.margin_2x)))
                        }
                    }

                    Column(verticalArrangement = Arrangement.Center, modifier = Modifier
                        .padding(top = dimensionResource(id = R.dimen.margin_2x))
                        .background(
                            color = Color.White
                        )
                        .fillMaxWidth()
                        .padding(
                            start = dimensionResource(id = R.dimen.margin),
                            end = dimensionResource(id = R.dimen.margin),
                            bottom = dimensionResource(id = R.dimen.margin)
                        )
                    ) {
                        TextFieldNormal(
                            textFieldValue = state.searchQuery,
                            onValueChange = {
                                searchContentViewModel.onQueryChange(it)
                            },
                            maxLines = 1,
                            labelText = stringResource(id = R.string.input_search_hint, stringResource(id = it.hintResId)),
                            modifier = Modifier.fillMaxWidth(),
                            focusRequester = focusRequester
                        )
                    }
                }
            }

            if (state.searchResult.isNotEmpty()) {
                LazyColumn() {
                   items(state.searchResult) { itemInfo->

                       Column(modifier = Modifier
                           .fillMaxWidth()
                           .clickable {
                               when (searchContentViewModel.searchContentInfo) {
                                   is SearchContentInfo.Commodity -> navigationActions.navToSellingScreen(SellingScreenInfo.Commodity(itemInfo as SellingCommodityInfo))
                                   is SearchContentInfo.Seller -> navigationActions.navToSellingScreen(SellingScreenInfo.Seller(itemInfo as Seller))
                                   is SearchContentInfo.Village -> navigationActions.navToSellingScreen(SellingScreenInfo.Village(itemInfo as VillageInfo))
                                   null -> {}
                               }
                           }) {
                           val label = when (searchContentViewModel.searchContentInfo) {
                               is SearchContentInfo.Commodity -> {
                                   itemInfo as SellingCommodityInfo
                                   val commodityMeasurementType =
                                       itemInfo.commodityDetail.commodityMeasurementType
                                   val unitResId =
                                       if (commodityMeasurementType == CommodityMeasurementType.Killogram) {
                                           R.string.kg
                                       } else {
                                           commodityMeasurementType.getMesurementTypeNameResId()
                                       }
                                   stringResource(
                                       id = R.string.x_y_per_z_unit, formatArgs = arrayOf(
                                           itemInfo.commodityDetail.name,
                                           itemInfo.pricePerMeasurementType,
                                           stringResource(id = unitResId)
                                       )
                                   )
                               }
                               is SearchContentInfo.Seller -> {
                                   itemInfo as Seller
                                   "${itemInfo.name} ${itemInfo.getLoyalityCardId()}"
                               }
                               is SearchContentInfo.Village -> {
                                   itemInfo as VillageInfo
                                   "${itemInfo.name} (${itemInfo.postalCode})"
                               }
                               null -> ""
                           }
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
    LaunchedEffect("FocusRequester"  ) {
        focusRequester.requestFocus()
    }
}