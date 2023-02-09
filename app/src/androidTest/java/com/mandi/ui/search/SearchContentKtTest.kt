package com.mandi.ui.search

import com.mandi.base.BaseTest
import com.mandi.R
import com.mandi.ui.selling.sellingScreen
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class SearchContentKtTest : BaseTest() {

    @Test
    fun verifySearchScreenInitialUi() {
        sellingScreen {
            clickComposeView(R.string.seller_name)
        } toSearchScreen {
            verifyUi(SearchContentType.SELLER_BY_NAME)
        }
    }

    @Test
    fun verifyNavigateBackToInitialScreen() {
        sellingScreen {
            clickComposeView(R.string.seller_name)
        } toSearchScreen {
            verifyUi(SearchContentType.SELLER_BY_NAME)
            selectSearchItemAt(1)
        } toSellingScreen {
            scrollToComposeText(R.string.seller_name)
            verifyUi()
            clickComposeView(R.string.loyality_card_identifier)
        } toSearchScreen {
            clickBackButton()
        } toSellingScreen {
            scrollToComposeText(R.string.seller_name)
            verifyUi()
        }
    }

}