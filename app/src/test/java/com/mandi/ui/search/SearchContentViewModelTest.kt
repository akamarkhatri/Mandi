package com.mandi.ui.search

import androidx.compose.ui.text.input.TextFieldValue
import app.cash.turbine.test
import com.mandi.BaseTest
import kotlinx.coroutines.test.runTest
import com.google.common.truth.Truth.assertThat
import com.mandi.model.Seller
import kotlinx.coroutines.*
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchContentViewModelTest: BaseTest() {

    private lateinit var searchContentViewModel: SearchContentViewModel
    @Before
    fun setUp() {
        searchContentViewModel = SearchContentViewModel(appContainer).apply { dispatcherProvider = testDispatcherProvider }
    }

    @Test
    fun verifySellerSearchQueryByName() {
        runTest {
            searchContentViewModel.searchContentInfo = SearchContentInfo.Seller(SearchContentType.SELLER_BY_NAME)
            val query = "R"
            searchContentViewModel.onQueryChange(TextFieldValue(query))
            delay(searchContentViewModel.debouncePeriod)
            searchContentViewModel.searchState.test {
                val searchResult = awaitItem().searchResult.find { (it as? Seller)?.name.orEmpty().contains(query) } as? Seller
                println("$searchResult")
                assertThat(searchResult?.name).contains(query)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun verifyEmptySearchResult() {
        runTest {
            searchContentViewModel.searchContentInfo = SearchContentInfo.Seller(SearchContentType.SELLER_BY_NAME)
            val query = ""
            searchContentViewModel.onQueryChange(TextFieldValue(query))
            delay(searchContentViewModel.debouncePeriod)
            searchContentViewModel.searchState.test {
                val searchResult = awaitItem().searchResult
                assertThat(searchResult.isEmpty()).isTrue()
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun verifyJobCancelIfUserUpdateContentBeforeDebouncePeriod() {
        runTest {
            searchContentViewModel.searchContentInfo = SearchContentInfo.Seller(SearchContentType.SELLER_BY_NAME)
            var query = "S"
            searchContentViewModel.onQueryChange(TextFieldValue(query))
            delay(searchContentViewModel.debouncePeriod/2)
            query = ""
            searchContentViewModel.onQueryChange(TextFieldValue(query))
            searchContentViewModel.searchState.test {
                val searchResult = awaitItem().searchResult
                assertThat(searchResult.isEmpty()).isTrue()
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun verifySearchQueryByLoyalityCardId() {
        runTest {
            searchContentViewModel.searchContentInfo = SearchContentInfo.Seller(SearchContentType.SELLER_BY_LC_ID)
            val query = "LCID_3"
            searchContentViewModel.onQueryChange(TextFieldValue(query))
            delay(searchContentViewModel.debouncePeriod)
            searchContentViewModel.searchState.test {
                val searchResult = awaitItem().searchResult.find { (it as? Seller)?.getLoyalityCardId().orEmpty().contains(query) } as? Seller
                println("$searchResult")
                assertThat(searchResult?.getLoyalityCardId()).isEqualTo(query)
                cancelAndConsumeRemainingEvents()
            }
        }
    }
}