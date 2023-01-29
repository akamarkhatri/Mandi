package com.mandi.ui.sellingcomplete

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mandi.ui.navigation.NavigationActions
import com.mandi.ui.navigation.SellerInventoryInfo

@Composable
fun SellingComplete(
    sellingCompleteViewModel: SellingCompleteViewModel,
    navigationActions: NavigationActions,
    sellerInventoryInfo: SellerInventoryInfo?
) {
    Column(modifier = Modifier.fillMaxWidth()) {
//        Greeting(name = "SellingScreen")

        Text(text = "Thanks! $sellerInventoryInfo")
        Button(onClick = navigationActions.navToSellingScreen) {
            Text(text = "Selling Complete Screen Click Me to Go Selling Screen")
        }
    }
}