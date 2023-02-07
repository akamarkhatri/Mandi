package com.mandi.ui.selling

import com.mandi.base.BaseRobot
import com.mandi.R

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
        verifyComposeViewDisabled("PrimaryButton_Sell My Produce")
    }
}