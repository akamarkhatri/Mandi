package com.mandi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mandi.ui.search.SearchContent
import com.mandi.ui.search.SearchContentInfo
import com.mandi.ui.search.SearchContentViewModel
import com.mandi.ui.selling.SellingScreen
import com.mandi.ui.selling.SellingViewModel
import com.mandi.ui.sellingcomplete.SellingComplete
import com.mandi.ui.sellingcomplete.SellingCompleteViewModel
import com.mandi.util.checkAndConvertToNull
import com.mandi.util.json

@Composable
fun MandiAppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = AppDestination.SellingScreen.routeWithArgs,
) {
    val navigationActions = remember(navController) {
        NavigationActions(navController)
    }
    NavHost(navController = navController, startDestination =  startDestination, modifier = modifier) {
        composable(route = AppDestination.SellingScreen.routeWithArgs, arguments = AppDestination.SellingScreen.arguments) {
            val sellingViewModel: SellingViewModel = hiltViewModel<SellingViewModel>().apply {
                val sellingScreenInfo = it.arguments?.getString(AppDestination.SellingScreen.KEY_SELLER_SCREEN_INFO)?.checkAndConvertToNull()?.let {
                    json.decodeFromString(SellingScreenInfo.serializer(), it)
                }
                when (sellingScreenInfo) {
                    is SellingScreenInfo.Commodity -> setCommodityInfo(sellingScreenInfo.data)
                    is SellingScreenInfo.Seller -> setSellerInfo(sellingScreenInfo.data)
                    is SellingScreenInfo.Village -> setVillageInfo(sellingScreenInfo.data)
                    null -> { }
                }
            }
            SellingScreen(sellingViewModel = sellingViewModel, navigationActions)
        }
        composable(route = AppDestination.SellingComplete.routeWithArgs,
            arguments = AppDestination.SellingComplete.arguments) {
            val sellingCompleteViewModel: SellingCompleteViewModel = hiltViewModel()
            val sellerInventoryInfo = it.arguments?.getString(AppDestination.SellingComplete.KEY_INVENTORY_INFO)?.let {
                json.decodeFromString(SellerInventoryInfo.serializer(), it)
            }
            SellingComplete(sellingCompleteViewModel = sellingCompleteViewModel, navigationActions, sellerInventoryInfo)
        }
        composable(AppDestination.SearchContent.routeWithArgs, arguments = AppDestination.SearchContent.arguments) {
            val searchContentViewModel: SearchContentViewModel = hiltViewModel<SearchContentViewModel>().apply {
                it.arguments?.getString(AppDestination.SearchContent.KEY_SEARCH_CONTENT_TYPE)?.let {
                    this.searchContentInfo = json.decodeFromString(SearchContentInfo.serializer(), it)
                }
            }
            SearchContent(searchContentViewModel = searchContentViewModel, navigationActions = navigationActions)
        }


    }
}