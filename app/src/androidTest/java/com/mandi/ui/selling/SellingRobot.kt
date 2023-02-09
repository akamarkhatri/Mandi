package com.mandi.ui.selling

import androidx.compose.ui.test.onAllNodesWithTag
import com.mandi.base.BaseRobot
import com.mandi.R
import com.mandi.ui.search.SearchRobot

fun sellingScreen(func: SellingRobot.() -> Unit) = SellingRobot().apply(func)

class SellingRobot: BaseRobot() {

    fun verifyUi() {
        verifyComposeTextVisible(R.string.app_name)
        verifyComposeTextVisible(R.string.seller_name)
        verifyComposeTextVisible(R.string.loyality_card_identifier)
        verifyComposeTextVisible(R.string.gross_price)
        scrollToComposeText(R.string.village)
        scrollToComposeText(R.string.commodity)
        scrollToComposeText(R.string.gross_weight)
        verifyGrossValueUi()
    }

    infix fun toSearchScreen(func: SearchRobot.() -> Unit): SearchRobot {
       return SearchRobot().apply(func)
    }

    fun verifyGrossValueUi(isVisible: Boolean = false) {
        val loaderTag = "Loader"
        val grossValueTag = "GrossValue"
        val buttonTag = "PrimaryButton_Sell My Produce"
        if (isVisible) {
            verifyComposeViewVisible(loaderTag)
            composeTestRule.waitUntil {
                composeTestRule.onAllNodesWithTag(loaderTag)
                    .fetchSemanticsNodes().isEmpty()
            }
            verifyComposeViewVisible(grossValueTag)
            verifyComposeViewEnabled(buttonTag)
        } else {
            verifyComposeViewInVisibleOrGone(loaderTag)
            verifyComposeViewDisabled(buttonTag)
            verifyComposeViewInVisibleOrGone(grossValueTag)
        }

    }

    fun addUpdateCommodityWt(commodityWt: String) {
        scrollToComposeView("WeightTextField", true)
        updateComposeText("WeightTextField", commodityWt, true)
    }
}