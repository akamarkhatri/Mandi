package com.mandi.base

import androidx.annotation.StringRes
import androidx.compose.ui.test.*

abstract class BaseRobot {
    private val composeTestRule by lazy {
        TestHelper.composeTestRule
    }

    fun verifyComposeTextVisible(text: String, useUnmergedTree: Boolean = false) {
        composeTestRule.onNode(hasText(text, ignoreCase = true), useUnmergedTree).assertIsDisplayed()
    }

    fun verifyComposeTextInVisibleOrGone(text: String, subString: Boolean, useUnmergedTree: Boolean = false) {
        composeTestRule.onNode(hasText(text, substring = subString), useUnmergedTree).assertDoesNotExist()
    }

    fun verifyComposeViewVisible(testTag: String) {
        composeTestRule.onNodeWithTag(testTag).assertIsDisplayed()
    }

    fun verifyComposeTextVisible(@StringRes stringId: Int, useUnmergedTree: Boolean = false) {
        verifyComposeTextVisible(composeTestRule.activity.getString(stringId), useUnmergedTree)
    }

    fun clickComposeText(text: String, useUnmergedTree: Boolean = false) {
        composeTestRule.onNode(hasText(text, ignoreCase = true), useUnmergedTree).performClick()
    }

    fun clickComposeText(@StringRes stringId: Int, useUnmergedTree: Boolean = false) {
        clickComposeText(composeTestRule.activity.getString(stringId), useUnmergedTree)
    }

    fun clickComposeView(testTag: String, useUnmergedTree: Boolean = false) {
        composeTestRule.onNodeWithTag(testTag = testTag, useUnmergedTree = useUnmergedTree)
            .performClick()
    }

    fun scrollToAndClickComposeView(testTag: String, useUnmergedTree: Boolean = false) {
        scrollToComposeView(testTag = testTag, useUnmergedTree = useUnmergedTree)
        clickComposeView(testTag = testTag, useUnmergedTree = useUnmergedTree)
    }

    fun scrollToComposeView(testTag: String, useUnmergedTree: Boolean = false) {
        composeTestRule.onNodeWithTag(testTag = testTag, useUnmergedTree = useUnmergedTree)
            .performScrollTo()
    }

    fun scrollToComposeText(text: String, useUnmergedTree: Boolean = false) {
        composeTestRule.onNode(hasText(text, ignoreCase = true), useUnmergedTree)
            .performScrollTo()
    }

    fun scrollToComposeText(@StringRes stringId: Int, useUnmergedTree: Boolean = false) {
        scrollToComposeText(composeTestRule.activity.getString(stringId), useUnmergedTree)
    }

    fun verifyTextInComposeView(testTag: String, text: String, useUnmergedTree: Boolean = false) {
        composeTestRule.onNodeWithTag(testTag = testTag, useUnmergedTree = useUnmergedTree)
            .assert(hasText(text, ignoreCase = true))
    }

    fun verifyChildInsideParentInComposeView(
        parentTestTag: String,
        childTestTag: String,
        useUnmergedTree: Boolean = false
    ) {
        composeTestRule.onNode(
            hasTestTag(parentTestTag) and hasAnyDescendant(hasTestTag(childTestTag)),
            useUnmergedTree = useUnmergedTree
        ).assertIsDisplayed()
    }

    fun verifyTextInsideParentInComposeView(
        parentTestTag: String,
        text: String,
        useUnmergedTree: Boolean = false
    ) {
        composeTestRule.onNode(
            hasTestTag(parentTestTag) and hasAnyDescendant(hasText(text, ignoreCase = true)),
            useUnmergedTree = useUnmergedTree
        ).assertIsDisplayed()
    }

    fun verifyTextInsideParentInComposeView(
        parentTestTag: String,
        @StringRes stringId: Int,
        useUnmergedTree: Boolean = false
    ) {
        verifyTextInsideParentInComposeView(
            parentTestTag = parentTestTag,
            text = composeTestRule.activity.getString(stringId),
            useUnmergedTree = useUnmergedTree
        )
    }

    fun verifyTextInComposeView(
        @StringRes stringId: Int,
        testTag: String,
        useUnmergedTree: Boolean = false
    ) {
        verifyTextInComposeView(
            testTag = testTag,
            text = composeTestRule.activity.getString(stringId),
            useUnmergedTree = useUnmergedTree
        )
    }

    fun verifyComposeViewDisabled(testTag: String, useUnmergedTree: Boolean = false) {
        composeTestRule.onNodeWithTag(testTag, useUnmergedTree).assertIsNotEnabled()
    }

    fun updateComposeText(testTag: String, text: String) {
        composeTestRule.onNodeWithTag(testTag).performTextReplacement(text)
    }
}