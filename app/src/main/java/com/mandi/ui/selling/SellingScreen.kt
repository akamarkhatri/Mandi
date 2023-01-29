package com.mandi.ui.selling

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mandi.ui.navigation.NavigationActions
import com.mandi.ui.navigation.SellerInventoryInfo

@Composable
fun SellingScreen(sellingViewModel: SellingViewModel, navigationActions: NavigationActions) {
    Column(modifier = Modifier.fillMaxWidth()) {
//        Greeting(name = "SellingScreen")

        Button(onClick = { navigationActions.navToSellingComplete(SellerInventoryInfo(
            "Amar",
            "Abc"
        ))}) {
            Text(text = "Selling Screen Click Me to Go Complete Screen")
        }
    }
}