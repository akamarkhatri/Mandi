package com.mandi.ui.navigation

import androidx.navigation.NavController
import com.mandi.ui.navigation.Destinations.SELLING_COMPLETE
import com.mandi.ui.navigation.Destinations.SELLING_SCREEN

object Destinations{
    const val SELLING_SCREEN = "selling_screen"
    const val SELLING_COMPLETE = "selling_complete"
}

class NavigationActions(navController: NavController) {
    val navToSellingScreen: () -> Unit = {
        navController.navigate(Destinations.SELLING_SCREEN) {
            launchSingleTop = true
            restoreState = true
            popUpTo(SELLING_COMPLETE) {
                this.inclusive = true
            }
        }
    }

    val navToSellingComplete: (sellerInventoryInfo: SellerInventoryInfo) ->Unit = {
        navController.navigate(Destinations.SELLING_COMPLETE) {
            launchSingleTop = true
            popUpTo(SELLING_SCREEN) {
                this.inclusive = true
            }
        }
    }
}

data class SellerInventoryInfo(val sellerName: String, val inventoryDescription: String)