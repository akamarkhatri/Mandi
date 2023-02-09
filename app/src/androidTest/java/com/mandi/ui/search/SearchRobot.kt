package com.mandi.ui.search

import androidx.compose.ui.test.*
import com.mandi.base.BaseRobot
import com.mandi.ui.selling.SellingRobot
import com.mandi.R

fun searchScreen(func: SearchRobot.() -> Unit) = SearchRobot().apply(func)

class SearchRobot: BaseRobot() {
    infix fun toSellingScreen(func: SellingRobot.() -> Unit): SellingRobot {
        return SellingRobot().apply(func)
    }

    fun searchText(query:String, searchContentType: SearchContentType) {
        val textFieldTestTag = "TextFieldNormal_${composeTestRule.activity.getString(
            R.string.input_search_hint,
            composeTestRule.activity.getString(searchContentType.hintResId)
        )}"
        updateComposeText(textFieldTestTag, query)
    }

    fun selectSearchItemAt(index: Int) {
        composeTestRule.waitUntil {
            composeTestRule.onAllNodesWithTag("SearchResult")
                .fetchSemanticsNodes().size == 1
        }
        clickIndexInsideLazyColumn("SearchResult",index)
    }

    fun verifyUi(searchContentType: SearchContentType) {
        verifyComposeViewVisible(composeTestRule.activity.getString(R.string.back))
        val title = composeTestRule.activity.getString(
            R.string.search_content,
            composeTestRule.activity.getString(searchContentType.titleResId)
        )
        verifyComposeTextVisible(title)
        verifyComposeViewInVisibleOrGone("SearchResult")
        verifyComposeViewInVisibleOrGone("Loader")
        searchText("a", searchContentType)
        verifyComposeViewVisible("Loader")
        composeTestRule.waitUntil {
            composeTestRule.onAllNodesWithTag("Loader")
                .fetchSemanticsNodes().isEmpty()
        }
        verifyComposeViewVisible("SearchResult")
    }

    fun clickBackButton() {
        val backLabel = composeTestRule.activity.getString(R.string.back)
       /* composeTestRule.waitUntil {
            composeTestRule.onAllNodesWithTag(backLabel)
                .fetchSemanticsNodes().isNotEmpty()
        }*/
        clickComposeView(backLabel, true)
    }
}