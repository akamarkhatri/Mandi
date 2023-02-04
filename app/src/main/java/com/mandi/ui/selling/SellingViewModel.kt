package com.mandi.ui.selling

import android.util.Log
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.mandi.data.AppContainer
import com.mandi.model.*
import com.mandi.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SellingViewModel @Inject internal constructor(appContainer: AppContainer) :
    BaseViewModel(appContainer) {

    private val state = MutableStateFlow(SellingScreenState())

    val uiState = state.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        state.value
    )

    fun setSellerInfo(sellerInfo: Seller?) {
        state.update { it.copy(sellerInfo = sellerInfo) }
    }

    fun setCommodityInfo(sellingCommodityInfo: SellingCommodityInfo?) {
        state.update {
            it.copy(
                selectedCommodityInfo = sellingCommodityInfo,
                sellingScreenErrorType = null,
                grossValue = null
            )
        }
    }

    fun setVillageInfo(villageInfo: VillageInfo?) {
        state.update {
            it.copy(
                selectedVillageInfo = villageInfo,
                sellingScreenErrorType = null,
                grossValue = null
            )
        }
    }

    private fun addError(screenErrorType: SellingScreenErrorType): Set<SellingScreenErrorType>? {
        return uiState.value.errorSet?.toMutableSet()?.apply {
            add(screenErrorType)
        }
    }

    private fun removeError(screenErrorType: SellingScreenErrorType): Set<SellingScreenErrorType>? {
        uiState.value.errorSet?.toMutableSet()?.remove(screenErrorType)
        return mutableSetOf(screenErrorType).apply {
            addAll(uiState.value.errorSet ?: setOf())
        }
    }

    fun log(msg: String) {
        Log.d("SellingViewModel", msg)
    }

    val event = fun(event: SellingScreenEvent) {
        when (event) {
            is SellingScreenEvent.OnGrossWtUpdate -> {
                state.update {
                    it.copy(
                        sellingCommodityWt = event.textFieldValue,
                        grossValue = null
                    )
                }
            }
            SellingScreenEvent.CalculateGrossValue -> {
                if (!canCalculateGrossValue()) {
                    return
                }
                state.update { it.copy(isLoading = true) }
                val grossValue = uiState.value.let { state ->
                    val quantity = state.sellingCommodityWt.text.toFloat()
                    val pricePerWt = state.selectedCommodityInfo?.pricePerMeasurementType ?: 0f
                    val loayalityIndex =
                        state.sellerInfo?.sellerRegistrationInfo?.getLoyalityIndexValue() ?: 0f
                    var valueWithoutIndex = quantity * pricePerWt
                    if (state.selectedCommodityInfo?.commodityDetail?.commodityMeasurementType == CommodityMeasurementType.Killogram) {
                        valueWithoutIndex *= 1000
                    }
                    valueWithoutIndex + (loayalityIndex * valueWithoutIndex) / 100
                }
                state.update { it.copy(isLoading = false, grossValue = grossValue) }

            }
        }
    }

    private fun canCalculateGrossValue(): Boolean {
       return state.value.let {
            it.sellerInfo == null || it.selectedVillageInfo == null || it.selectedCommodityInfo == null || it.sellingCommodityWt.text.isEmpty()
        }.not()
    }
}

sealed class SellingScreenEvent {

    //    class VillageSelected(val villageInfo: VillageInfo): SellingScreenEvent()
    class OnGrossWtUpdate(val textFieldValue: TextFieldValue) : SellingScreenEvent()

    object CalculateGrossValue : SellingScreenEvent()
}

data class SellingScreenState(
    var isLoading: Boolean = false,
    var sellerInfo: Seller? = null,
    var sellingCommodityWt: TextFieldValue = TextFieldValue("", TextRange.Zero),
    var selectedVillageInfo: VillageInfo? = null,
    var sellingScreenErrorType: SellingScreenErrorType? = null,
    var selectedCommodityInfo: SellingCommodityInfo? = null,
    var grossValue: Float? = null,
    val errorSet: Set<SellingScreenErrorType>? = null
)

enum class SellingScreenErrorType {
    SELLER_NOT_FOUND,
    VILLAGE_NOT_SELECTED,
    SELLER_NOT_SELECTED
}

