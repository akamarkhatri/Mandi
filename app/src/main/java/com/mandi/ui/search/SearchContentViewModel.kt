package com.mandi.ui.search

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.mandi.R
import com.mandi.data.AppContainer
import com.mandi.data.successOr
import com.mandi.model.CommodityDetail
import com.mandi.model.Seller
import com.mandi.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchContentViewModel @Inject internal constructor(appContainer: AppContainer): BaseViewModel(appContainer) {
    private val _searchState by lazy { MutableStateFlow(SearchContentState(searchContentInfo = searchContentInfo)) }
    val searchState by lazy {
        _searchState.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            _searchState.value
        )
    }

    private var searchJob: Job? = null
    var debouncePeriod = 500L
//    var searchContentType: SearchContentType? = null
    var searchContentInfo: SearchContentInfo? = null

    fun onQueryChange(query: TextFieldValue) {

        logMsg("OnQueryChange ->${query.text}")
        updateState(_searchState.value.copy(
            isLoading = false,
            searchQuery = query
        ))
        searchJob?.cancel()
        if (query.text.trim().isEmpty()) {
            updateState(_searchState.value.copy(searchResult = listOf()))
        } else {
            viewModelScope.launch {
                searchContentInfo?.getType()?.let {
                    updateState(_searchState.value.copy(isLoading = true))
                    delay(debouncePeriod)
                    performSearch(it, query.text)
                }
            }
        }

    }

    private fun logMsg(msg: String) {
        Log.d("ViewModel", msg)
    }

    private fun updateState(searchContentState: SearchContentState) {
        _searchState.update { searchContentState }
    }

    private suspend fun performSearch(searchContentType: SearchContentType, query: String) {
        val result = when (searchContentType) {
            SearchContentType.SELLER_BY_NAME -> {
                appContainer.sellerRepository.getSellerByName(query).successOr(listOf())
            }
            SearchContentType.SELLER_BY_LC_ID -> {
                appContainer.sellerRepository.getSellerById(query).successOr(listOf())
            }
            SearchContentType.VILLAGE -> {
                appContainer.villageRepository.getAllVillage(query).successOr(listOf())
            }
            SearchContentType.COMMODITY -> {
                appContainer.villageRepository.getSellingCommodities(
                    (searchContentInfo as? SearchContentInfo.Commodity)?.villageId.orEmpty(),
                    query
                ).successOr(listOf())
            }
        }
        updateState(_searchState.value.copy(searchResult = result, isLoading = false))
    }
}

data class SearchContentState(
    val isLoading: Boolean = false,
    val searchQuery: TextFieldValue = TextFieldValue(
        "",
        TextRange.Zero
    ),
    val searchResult: List<Any> = listOf(),
    val searchContentInfo: SearchContentInfo? = null
)

@kotlinx.serialization.Serializable
enum class SearchContentType(@StringRes val titleResId: Int, @StringRes val hintResId: Int) {
    SELLER_BY_NAME(R.string.seller, R.string.seller_name),
    SELLER_BY_LC_ID(R.string.seller, R.string.loyality_card_id),
    VILLAGE(R.string.village, R.string.village_name),
    COMMODITY(R.string.commodity, R.string.commodity)
}

//data class SearchContentDetail(val searchContentType: SearchContentType, )

@kotlinx.serialization.Serializable
sealed class SearchContentInfo {
    @kotlinx.serialization.Serializable
    data class Seller(val contentType: SearchContentType): SearchContentInfo()

    @kotlinx.serialization.Serializable
    data class Village(val contentType: SearchContentType): SearchContentInfo()

    @kotlinx.serialization.Serializable
    data class Commodity(val contentType: SearchContentType, val villageId: String): SearchContentInfo();

    fun getType(): SearchContentType {
        return when (this) {
            is Commodity -> contentType
            is Seller ->  contentType
            is Village -> contentType
        }
    }
}