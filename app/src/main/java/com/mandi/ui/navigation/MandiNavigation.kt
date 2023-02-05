package com.mandi.ui.navigation

import androidx.navigation.NavController
import com.mandi.model.Seller
import com.mandi.model.SellingCommodityInfo
import com.mandi.model.VillageInfo
import com.mandi.ui.search.SearchContentInfo
import com.mandi.util.json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class NavigationActions(navController: NavController) {
    val navToSellingScreen: (sellingScreenInfo: SellingScreenInfo?) -> Unit = {

        if (it == null)
        {
            navController.popBackStack()
        }
        navController.navigate("${AppDestination.SellingScreen.route}?${AppDestination.SellingScreen.KEY_SELLER_SCREEN_INFO}=${json.encodeToString(it)}")
        {
            launchSingleTop = true
            popUpTo(AppDestination.SellingScreen.routeWithArgs){
                this.saveState = it != null
                this.inclusive = it == null

            }
        }
    }

    val navToSellingComplete: (sellerInventoryInfo: SellerInventoryInfo) ->Unit = {

        navController.navigate("${AppDestination.SellingComplete.route}/${Json.encodeToString(it)}") {
            launchSingleTop = true
            popUpTo(AppDestination.SellingScreen.routeWithArgs) {
                this.inclusive = true
            }
        }
    }

    val navToSearchContentScreen: (searchContentInfo: SearchContentInfo?) -> Unit = {
        navController.navigate("${AppDestination.SearchContent.route}/${json.encodeToString(it)}") {
            launchSingleTop = true
        }
    }
    val popUp: (seller:Seller?) -> Unit = {
        navController.previousBackStackEntry?.savedStateHandle?.set(AppDestination.SellingScreen.KEY_SELLER_SCREEN_INFO, json.encodeToString(it))
        navController.navigateUp()
    }
}

@kotlinx.serialization.Serializable
data class SellerInventoryInfo(val sellerName: String, val inventoryDescription: String)

@kotlinx.serialization.Serializable
sealed class SellingScreenInfo{
    @kotlinx.serialization.Serializable
    data class Village(val data: VillageInfo): SellingScreenInfo()
    @kotlinx.serialization.Serializable
    data class Seller(val data: com.mandi.model.Seller): SellingScreenInfo()
    @kotlinx.serialization.Serializable
    data class Commodity(val data: SellingCommodityInfo): SellingScreenInfo()

//    @kotlinx.serialization.Serializable
//    object General: SellingScreenInfo()
}