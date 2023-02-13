package com.mandi.ui.selling

import com.mandi.base.BaseTest
import com.mandi.R
import com.mandi.ui.search.SearchContentType
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Test

@HiltAndroidTest
class SellingScreenKtTest: BaseTest() {

    @Test
    fun verifySellingScreenInitialUi() {
        sellingScreen {
            verifyUi()
        }
    }

    @Test
    fun verifyGrossValueCalculated() {
        sellingScreen {
            verifyGrossValueUi(false)
            clickComposeView(R.string.seller_name)
        } toSearchScreen {
            searchText("R", SearchContentType.SELLER_BY_NAME)
            selectSearchItemAt(1)
        } toSellingScreen {
            scrollToComposeView(R.string.commodity)
            verifyComposeViewDisabled(getStringValue(R.string.commodity))
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
            verifyGrossValueUi(true)
        }
    }
}