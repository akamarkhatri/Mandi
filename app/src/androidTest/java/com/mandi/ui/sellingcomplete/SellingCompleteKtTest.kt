package com.mandi.ui.sellingcomplete

import com.mandi.R
import com.mandi.base.BaseTest
import com.mandi.ui.search.SearchContentType
import com.mandi.ui.selling.sellingScreen
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class SellingCompleteKtTest : BaseTest() {

    @Test
    fun verifyScreenUiAndNavigateBackToSellerScreen() {
        sellingScreen {
            clickComposeView(R.string.seller_name)
        } toSearchScreen {
            searchText("R", SearchContentType.SELLER_BY_NAME)
            selectSearchItemAt(1)
        } toSellingScreen {
            scrollToComposeView(R.string.commodity)
            clickComposeView(R.string.village)
        } toSearchScreen {
            searchText("a", SearchContentType.VILLAGE)
            selectSearchItemAt(0)
        } toSellingScreen {
            scrollToComposeView(R.string.commodity)
            clickComposeView(R.string.commodity)
        } toSearchScreen {
            searchText("a", SearchContentType.COMMODITY)
            selectSearchItemAt(0)
        } toSellingScreen {
            addUpdateCommodityWt("5")
            sellProduce {
                verifyUi()
                clickSellMoreButton()
            }
            verifyUi()
        }
    }

    @Test
    fun verifyAppClosesOnBackPress() {
        sellingScreen {
            clickComposeView(R.string.seller_name)
        } toSearchScreen {
            searchText("R", SearchContentType.SELLER_BY_NAME)
            selectSearchItemAt(1)
        } toSellingScreen {
            scrollToComposeView(R.string.commodity)
            clickComposeView(R.string.village)
        } toSearchScreen {
            searchText("a", SearchContentType.VILLAGE)
            selectSearchItemAt(0)
        } toSellingScreen {
            scrollToComposeView(R.string.commodity)
            clickComposeView(R.string.commodity)
        } toSearchScreen {
            searchText("a", SearchContentType.COMMODITY)
            selectSearchItemAt(0)
        } toSellingScreen {
            addUpdateCommodityWt("5")
            sellProduce {
                verifyUi()
                backPress()
                verifyActivityStartFinishing()
            }
        }
    }

}