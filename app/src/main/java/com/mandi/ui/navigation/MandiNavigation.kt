package com.mandi.ui.navigation

import androidx.navigation.NavController
import com.google.gson.Gson

class NavigationActions(navController: NavController) {
    val navToSellingScreen: () -> Unit = {
        navController.navigate(AppDestination.SellingScreen.route) {
            launchSingleTop = true
            popUpTo(AppDestination.SellingComplete.routeWithArgs){
                this.inclusive = true
            }
        }
    }

    val navToSellingComplete: (sellerInventoryInfo: SellerInventoryInfo) ->Unit = {
        navController.navigate("${AppDestination.SellingComplete.route}/${Gson().toJson(it)}") {
            launchSingleTop = true
            popUpTo(AppDestination.SellingScreen.route) {
                this.inclusive = true
            }
        }
    }
}

data class SellerInventoryInfo(val sellerName: String, val inventoryDescription: String)