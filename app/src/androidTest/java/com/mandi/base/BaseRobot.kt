package com.mandi.base

import androidx.annotation.StringRes
import androidx.compose.ui.test.*

abstract class BaseRobot {
    val composeTestRule by lazy {
        TestHelper.composeTestRule
    }

    fun verifyComposeTextVisible(text: String, useUnmergedTree: Boolean = false) {
        composeTestRule.onNode(hasText(text, ignoreCase = true), useUnmergedTree).assertIsDisplayed()
    }

    fun verifyComposeViewInVisibleOrGone(text: String, subString: Boolean, useUnmergedTree: Boolean = false) {
        composeTestRule.onNode(hasText(text, substring = subString), useUnmergedTree).assertDoesNotExist()
    }

    fun verifyComposeViewVisible(testTag: String, useUnmergedTree: Boolean = false) {
        composeTestRule.onNodeWithTag(testTag, useUnmergedTree).assertIsDisplayed()
    }

    fun verifyComposeViewInVisibleOrGone(textTag: String, useUnmergedTree: Boolean = false) {
        composeTestRule.onNodeWithTag(textTag, useUnmergedTree).assertDoesNotExist()
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

    fun clickComposeView(@StringRes testTagResId: Int, useUnmergedTree: Boolean = false) {
       clickComposeView(composeTestRule.activity.getString(testTagResId), useUnmergedTree)
    }

    fun scrollToAndClickComposeView(testTag: String, useUnmergedTree: Boolean = false) {
        scrollToComposeView(testTag = testTag, useUnmergedTree = useUnmergedTree)
        clickComposeView(testTag = testTag, useUnmergedTree = useUnmergedTree)
    }

    fun scrollToComposeView(testTag: String, useUnmergedTree: Boolean = false) {
        composeTestRule.onNodeWithTag(testTag = testTag, useUnmergedTree = useUnmergedTree)
            .performScrollTo()
    }

    fun scrollToComposeView(@StringRes testTag: Int, useUnmergedTree: Boolean = false) {
       scrollToComposeView(composeTestRule.activity.getString(testTag), useUnmergedTree)
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

    final fun getStringValue(@StringRes resId: Int): String {
        return composeTestRule.activity.getString(resId)
    }

    fun verifyComposeViewEnabled(testTag: String, useUnmergedTree: Boolean = false) {
        composeTestRule.onNodeWithTag(testTag, useUnmergedTree).assertIsEnabled()
    }

    fun updateComposeText(testTag: String, text: String, canPerformImeAction: Boolean = false) {
        composeTestRule.onNodeWithTag(testTag).performTextReplacement(text)
        if (canPerformImeAction) {
            composeTestRule.onNodeWithTag(testTag).performImeAction()
        }
    }

    fun scrollToItemInsideLazyColumn(lazyColumnTestTag: String, itemTestTag: String) {
        composeTestRule.onNodeWithTag(lazyColumnTestTag).performScrollToNode(hasTestTag(itemTestTag))
    }

    fun scrollToItemInsideLazyColumnAndClick(lazyColumnTestTag: String, itemTestTag: String) {
        composeTestRule.onNodeWithTag(lazyColumnTestTag).performScrollToNode(hasTestTag(itemTestTag))
        clickComposeView(testTag = itemTestTag)
    }

    fun clickIndexInsideLazyColumn(lazyColumnTestTag: String, index: Int) {
        composeTestRule.onNodeWithTag(lazyColumnTestTag).onChildAt(index).performClick()
    }
}