package com.mandi.ui.selling

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mandi.ui.base.compose.PrimaryButton
import com.mandi.ui.navigation.NavigationActions
import com.mandi.ui.navigation.SellerInventoryInfo

@Composable
fun SellingScreen(sellingViewModel: SellingViewModel, navigationActions: NavigationActions) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {

        PrimaryButton(text = "Selling Screen Click Me to Go Complete Screen", onClick = {
            navigationActions.navToSellingComplete(SellerInventoryInfo(
                "Amar",
                "Please ensure you received 20,623.23 INR for 23 Tonnes of your produce."
            ))
        } )
    }
}