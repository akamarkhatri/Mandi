package com.mandi.ui.selling

import com.mandi.base.BaseTest
import dagger.hilt.android.testing.HiltAndroidTest
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

        }
    }
}