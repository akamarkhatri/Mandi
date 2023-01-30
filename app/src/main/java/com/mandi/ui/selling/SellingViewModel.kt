package com.mandi.ui.selling

import android.util.Log
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.mandi.data.AppContainer
import com.mandi.data.Result
import com.mandi.model.*
import com.mandi.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SellingViewModel @Inject internal constructor(appContainer: AppContainer) :
    BaseViewModel(appContainer) {
    init {
        fetchAllVillage()
    }

    private val state = MutableStateFlow(SellingScreenState())

    val uiState = state.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        state.value
    )
//    /mutableStateOf(SellingScreenState())
    //   private set

    private fun fetchAllVillage() {
        viewModelScope.launch {
            val result = appContainer.villageRepository.getAllVillage()
            if (result is Result.Success) {
                state.update { it.copy(allVillageList = result.data) }
            }
        }
    }

    fun fetchSeller(textFieldValueType: TextFieldValueType) {
        viewModelScope.launch {
            state.update {
                it.copy(
                    isLoading = true,
                    sellerInfo = null,
                    isSellerSelected = false,
                    moreThanOneSellerFound = false,
                    grossValue = null
                )
            }
            val result = when (textFieldValueType) {
                TextFieldValueType.Seller -> {
                    if (state.value.sellerNameValue.text.isEmpty()) {
                        return@launch state.update { it.copy(isLoading = false) }
                    } else {

                        appContainer.sellerRepository.getSellerByName(state.value.sellerNameValue.text)
                    }
                }
                else -> {
                    if (state.value.sellerLoyalityCardValue.text.isEmpty()) {
                        return@launch state.update { it.copy(isLoading = false) }
                    } else {
                        appContainer.sellerRepository.getSellerById(state.value.sellerLoyalityCardValue.text)
                    }
                }
            }
            when (result) {
                is Result.Error -> {
                    state.update {
                        it.copy(isLoading = false,
                            errorSet = addError(SellingScreenErrorType.SELLER_NOT_FOUND))
                    }
                }
                is Result.Success -> {
                    val sellerList = result.data
                    when (sellerList.size) {
                        1 -> {
                            val seller = sellerList.first()
                            val loyalityCardId = when (seller.sellerRegistrationInfo) {
                                is SellerRegistrationInfo.Register -> seller.sellerRegistrationInfo.loyalityCardId
                                is SellerRegistrationInfo.Unregister -> ""
                            }
                            state.update {
                                it.copy(
                                    isLoading = false,
                                    sellerInfo = seller,
                                    isSellerSelected = true,
                                    sellerList = listOf(),
                                    moreThanOneSellerFound = false,
                                    sellerNameValue = TextFieldValue(text = seller.name,
                                        selection = TextRange(seller.name.length)),
                                    sellerLoyalityCardValue = TextFieldValue(text = loyalityCardId,
                                        selection = TextRange(loyalityCardId.length)),
                                    grossValue = null,
                                    errorSet = removeError(SellingScreenErrorType.SELLER_NOT_FOUND)
                                )
                            }
                        }
                        else -> {
                            state.update {
                                it.copy(
                                    isLoading = false,
                                    sellerInfo = null,
                                    isSellerSelected = false,
                                    sellerList = sellerList,
                                    moreThanOneSellerFound = true,
                                    grossValue = null
                                )
                            }
                        }
                    }
                }
            }
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

    fun textFieldValueChange(
        textFieldValue: TextFieldValue,
        textFieldValueType: TextFieldValueType,
    ) {
        log("textfieldValueChange -> $textFieldValue $textFieldValueType")
        when (textFieldValueType) {
            TextFieldValueType.Seller -> {
                state.update {
                    it.copy(
                        sellerNameValue = textFieldValue,
                        isSellerSelected = false,
                        sellerList = listOf(),
                        moreThanOneSellerFound = false,
                        grossValue = null
                    )
                }
            }
            TextFieldValueType.LoyalityCard -> {
                state.update {
                    it.copy(
                        isSellerSelected = false,
                        sellerLoyalityCardValue = textFieldValue,
                        sellerList = listOf(),
                        moreThanOneSellerFound = false,
                        grossValue = null
                    )
                }
            }
            TextFieldValueType.GrossWt -> {
                state.update {
                    it.copy(
                        sellingCommodityWt = textFieldValue,
                        grossValue = null
                    )
                }
            }
        }
    }

    val event = fun(event: SellingScreenEvent) {
        when (event) {
            is SellingScreenEvent.FetchSeller -> fetchSeller(event.textFieldValueType)
            is SellingScreenEvent.OnTextFieldValueChange -> textFieldValueChange(event.textFieldValue,
                event.textFieldValueType)
//                is SellingScreenEvent.VillageSelected -> onVillageSelected(event.villageInfo)
            is SellingScreenEvent.UpdateSellersBottomSheetStatus -> updateSellerBottomSheetStatus(
                event.value,
                event.selectedSeller)
            is SellingScreenEvent.UpdateVillageBottomSheetStatus -> updateVillageBottomSheetStatus(
                event.value,
                event.villageInfo)
            is SellingScreenEvent.UpdateCommodityBottomSheetStatus -> updateSelectedCommodityBottomInfo(
                event.value,
                event.selectedCommodityInfo)
            SellingScreenEvent.CalculateGrossValue -> {
                if (!canCalulateGrossValue()) {
                    return
                }
                state.update { it.copy(isLoading = true) }
                val grossValue = uiState.value.let { state ->
                    val quantity = state.sellingCommodityWt.text.toFloat()
                    val pricePerWt = state.selectedCommodityInfo?.pricePerMeasurementType ?: 0f
                    val loayalityIndex =
                        state.sellerInfo?.sellerRegistrationInfo?.loyalityIndex ?: 0f
                    var valueWithoutIndex = quantity * pricePerWt
                    if (state.selectedCommodityInfo?.commodityDetail?.commodityMeasurementType == CommodityMeasurementType.Killogram) {
                        valueWithoutIndex *= 1000
                    }
                    valueWithoutIndex + loayalityIndex * valueWithoutIndex
                }
                state.update { it.copy(isLoading = false, grossValue = grossValue) }

            }
        }
    }

    private fun canCalulateGrossValue(): Boolean {
        val value = state.value.let {
            it.sellerInfo == null || it.selectedVillageInfo == null || it.selectedCommodityInfo == null || it.sellingCommodityWt.text.isEmpty()
        }
        return value.not()
    }

    private fun updateSelectedCommodityBottomInfo(
        value: Boolean,
        sellingCommodityInfo: SellingCommodityInfo?,
    ) {
        state.update { it.copy(canShowCommodityBottomSheet = value) }
        sellingCommodityInfo?.let {
            state.update {
                it.copy(
                    selectedCommodityInfo = sellingCommodityInfo,
                    sellingScreenErrorType = null,
                    grossValue = null
                )
            }
        }
    }

    private fun updateVillageBottomSheetStatus(value: Boolean, villageInfo: VillageInfo?) {
        state.update { it.copy(canShowVillageBottomSheet = value) }
        villageInfo?.let {
            state.update {
                it.copy(
                    selectedVillageInfo = villageInfo,
                    sellingScreenErrorType = null,
                    grossValue = null
                )
            }
        }
    }

    private fun updateSellerBottomSheetStatus(value: Boolean, selectedSeller: Seller?) {
        state.update {
            it.copy(
                sellerInfo = selectedSeller,
                isSellerSelected = selectedSeller != null,
                sellerList = listOf(),
                moreThanOneSellerFound = false,
                sellerNameValue = TextFieldValue(selectedSeller?.name.orEmpty(),
                    TextRange(selectedSeller?.name.orEmpty().length)),
                sellerLoyalityCardValue = TextFieldValue(selectedSeller?.getLoyalityCardId()
                    .orEmpty(), TextRange(selectedSeller?.getLoyalityCardId().orEmpty().length)),
                grossValue = null,
                errorSet = removeError(SellingScreenErrorType.SELLER_NOT_FOUND)
            )
        }
    }
}

sealed class SellingScreenEvent {
    class FetchSeller(val textFieldValueType: TextFieldValueType) : SellingScreenEvent()

    //    class VillageSelected(val villageInfo: VillageInfo): SellingScreenEvent()
    class OnTextFieldValueChange(
        val textFieldValue: TextFieldValue,
        val textFieldValueType: TextFieldValueType,
    ) : SellingScreenEvent()

    class UpdateVillageBottomSheetStatus(val value: Boolean, val villageInfo: VillageInfo? = null) :
        SellingScreenEvent()

    class UpdateSellersBottomSheetStatus(val value: Boolean, val selectedSeller: Seller? = null) :
        SellingScreenEvent()

    class UpdateCommodityBottomSheetStatus(
        val value: Boolean,
        val selectedCommodityInfo: SellingCommodityInfo? = null,
    ) : SellingScreenEvent()

    object CalculateGrossValue : SellingScreenEvent()
}

data class SellingScreenState(
    var isLoading: Boolean = false,
    var sellerInfo: Seller? = null,
    var isSellerSelected: Boolean = false,
    var sellingCommodityWt: TextFieldValue = TextFieldValue("", TextRange.Zero),
    var selectedVillageInfo: VillageInfo? = null,
    var allVillageList: List<VillageInfo> = listOf(),
    var sellerList: List<Seller> = listOf(),
    var moreThanOneSellerFound: Boolean = false,
    var sellerNameValue: TextFieldValue = TextFieldValue(text = sellerInfo?.name.orEmpty(),
        selection = TextRange(sellerInfo?.name.orEmpty().length)),
    var sellerLoyalityCardValue: TextFieldValue = TextFieldValue(text = sellerInfo?.name.orEmpty(),
        selection = TextRange(sellerInfo?.name.orEmpty().length)),
    var sellingScreenErrorType: SellingScreenErrorType? = null,
    var canShowVillageBottomSheet: Boolean = false,
    var canShowCommodityBottomSheet: Boolean = false,
    var selectedCommodityInfo: SellingCommodityInfo? = null,
    var grossValue: Float? = null,
    val errorSet: Set<SellingScreenErrorType>? = null,

    )

enum class SellingScreenErrorType {
    SELLER_NOT_FOUND,
    VILLAGE_NOT_SELECTED,
    SELLER_NOT_SELECTED
}

enum class TextFieldValueType {
    Seller,
    LoyalityCard,
    GrossWt
}