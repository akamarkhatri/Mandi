package com.mandi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.mandi.ui.selling.SellingScreen
import com.mandi.ui.selling.SellingViewModel
import com.mandi.ui.sellingcomplete.SellingComplete
import com.mandi.ui.sellingcomplete.SellingCompleteViewModel

@Composable
fun MandiAppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = AppDestination.SellingScreen.route,
) {
    val navigationActions = remember(navController) {
        NavigationActions(navController)
    }
    NavHost(navController = navController, startDestination =  startDestination, modifier = modifier) {
        composable(AppDestination.SellingScreen.route) {
            val sellingViewModel: SellingViewModel = hiltViewModel()
            SellingScreen(sellingViewModel = sellingViewModel, navigationActions)
        }
        composable(route = AppDestination.SellingComplete.routeWithArgs,
            arguments = AppDestination.SellingComplete.arguments) {
            val sellingCompleteViewModel: SellingCompleteViewModel = hiltViewModel()
            val sellerInventoryInfo =
                Gson().fromJson(it.arguments?.getString(AppDestination.SellingComplete.KEY_INVENTORY_INFO), SellerInventoryInfo::class.java)
            SellingComplete(sellingCompleteViewModel = sellingCompleteViewModel, navigationActions, sellerInventoryInfo)
        }
    }
}