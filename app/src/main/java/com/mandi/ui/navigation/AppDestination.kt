package com.mandi.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.mandi.ui.search.SearchContentType

sealed class AppDestination(val route: String) {
    object SellingScreen: AppDestination("selling_screen") {
        const val KEY_SELLER_SCREEN_INFO = "sellerScreenInfo"
        val routeWithArgs = "$route?$KEY_SELLER_SCREEN_INFO={$KEY_SELLER_SCREEN_INFO}"
//        val routeWithArgs = "$route?$KEY_SELLER_SCREEN_INFO={$KEY_SELLER_SCREEN_INFO}"
        val arguments = listOf(
            navArgument(KEY_SELLER_SCREEN_INFO) {
                type = NavType.StringType
                nullable = true
//                defaultValue = null
            }
        )
    }

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

    object SearchContent: AppDestination("search_content") {
        const val KEY_SEARCH_CONTENT_TYPE = "searchContentType"
        val routeWithArgs = "$route/{$KEY_SEARCH_CONTENT_TYPE}"
        val arguments = listOf(
            navArgument(KEY_SEARCH_CONTENT_TYPE) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        )
    }
}