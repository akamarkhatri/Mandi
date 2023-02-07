package com.mandi.base

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mandi.LaunchApp
import com.mandi.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.rules.RuleChain
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class BaseTest {
    private val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val rule: RuleChain = RuleChain.outerRule(hiltRule)
        .around(composeTestRule)

    @Before
    fun setUpHelper() {
       TestHelper.composeTestRule = composeTestRule
    }
}