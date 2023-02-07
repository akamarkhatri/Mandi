package com.mandi.base

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.mandi.MainActivity

object TestHelper {
    lateinit var composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
}