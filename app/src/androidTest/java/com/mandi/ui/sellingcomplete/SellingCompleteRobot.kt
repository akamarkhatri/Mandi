package com.mandi.ui.sellingcomplete

import com.mandi.base.BaseRobot
import com.mandi.R

class SellingCompleteRobot: BaseRobot() {
    private val bottomButtonTag = "PrimaryButton_Sell More"

    fun verifyUi() {
        verifyDrawableInComposeIsDisplayed(R.drawable.fruit_box)
        verifyComposeTextVisible("Thank you", isSubstring = true)
        verifyComposeTextVisible("for selling your quality produce.", isSubstring = true)
        verifyComposeTextVisible("Please ensure you received", isSubstring = true)
        verifyComposeTextVisible("of your produce.", isSubstring = true)
        verifyComposeViewVisible(bottomButtonTag)
    }

    fun clickSellMoreButton() {
        clickComposeView(bottomButtonTag)
    }

}