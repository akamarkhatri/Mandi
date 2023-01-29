package com.mandi.ui.navigation

import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class AppDestination(val route: String) {
    object SellingScreen: AppDestination("selling_screen")
    object SellingComplete: AppDestination("selling_complete") {
        const val KEY_INVENTORY_INFO = "inventoryInfo"
        val routeWithArgs = "$route/{$KEY_INVENTORY_INFO}"
        val arguments = listOf(
            navArgument(KEY_INVENTORY_INFO) { type = NavType.StringType
            nullable = true
                defaultValue = null
            }
        )
    }
}