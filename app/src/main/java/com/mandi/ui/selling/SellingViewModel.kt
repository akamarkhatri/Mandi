package com.mandi.ui.selling

import android.util.Log
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.mandi.data.AppContainer
import com.mandi.model.*
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
class SellingViewModel @Inject internal constructor(appContainer: AppContainer) :
    BaseViewModel(appContainer) {

    private val state = MutableStateFlow(SellingScreenState())
    private var calculateGrossAmtJob: Job? = null

    val uiState = state.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        state.value
    )

    fun setSellerInfo(sellerInfo: Seller?) {
        state.update { it.copy(sellerInfo = sellerInfo) }
        chkAndCalculateGrossValue()
    }

    fun setCommodityInfo(sellingCommodityInfo: SellingCommodityInfo?) {
        state.update {
            it.copy(
                selectedCommodityInfo = sellingCommodityInfo,
                sellingScreenErrorType = null,
                grossValue = null
            )
        }
        chkAndCalculateGrossValue()
    }

    fun setVillageInfo(villageInfo: VillageInfo?) {
        state.update {
            it.copy(
                selectedVillageInfo = villageInfo,
                sellingScreenErrorType = null,
                grossValue = null,
                selectedCommodityInfo = null
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
                chkAndCalculateGrossValue()
            }
        }
    }

    private fun chkAndCalculateGrossValue() {
        calculateGrossAmtJob?.cancel()
        state.update { it.copy(grossValue = null, isLoading = false) }
        val commodityWt = uiState.value.sellingCommodityWt.text.toFloatOrNull()
        commodityWt ?: return
        if (canCalculateGrossValue().not()) {
            return
        }
        calculateGrossAmtJob = viewModelScope.launch {
            state.update { it.copy(isLoading = true) }
            delay(500)
            val grossValue = uiState.value.let { state ->
                val pricePerWt = state.selectedCommodityInfo?.pricePerMeasurementType ?: 0f
                val loyalityIndex =
                    state.sellerInfo?.sellerRegistrationInfo?.getLoyalityIndexValue() ?: 0f
                var valueWithoutIndex = commodityWt * pricePerWt
                if (state.selectedCommodityInfo?.commodityDetail?.commodityMeasurementType == CommodityMeasurementType.Killogram) {
                    valueWithoutIndex *= 1000
                }
                valueWithoutIndex + (loyalityIndex * valueWithoutIndex) / 100
            }
            state.update { it.copy(isLoading = false, grossValue = grossValue) }
        }
    }

    private fun canCalculateGrossValue(): Boolean {
       return state.value.let {
            it.sellerInfo == null || it.selectedVillageInfo == null || it.selectedCommodityInfo == null || it.sellingCommodityWt.text.isEmpty()
        }.not()
    }
}

sealed class SellingScreenEvent {
    class OnGrossWtUpdate(val textFieldValue: TextFieldValue) : SellingScreenEvent()
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

