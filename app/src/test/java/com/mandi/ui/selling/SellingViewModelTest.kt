package com.mandi.ui.selling

import androidx.compose.ui.text.input.TextFieldValue
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.mandi.BaseTest
import com.mandi.data.seller.impl.allSellers
import com.mandi.data.village.impl.village1
import com.mandi.model.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SellingViewModelTest: BaseTest() {

    private lateinit var sellingViewModel: SellingViewModel
    @Before
    fun setUp() {
        sellingViewModel = SellingViewModel(appContainer).apply {
            dispatcherProvider = testDispatcherProvider
        }
    }

    @Test
    fun verifySellerInfoAfterSearch() {
        runTest {
            assertThat(sellingViewModel.uiState.value.sellerInfo).isEqualTo(null)
            val seller = allSellers.first()
            sellingViewModel.setSellerInfo(seller)
            sellingViewModel.uiState.test {
                assertThat(awaitItem().sellerInfo).isEqualTo(seller)
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun verifyVillageInfoAfterSearch() {
        runTest {
            assertThat(sellingViewModel.uiState.value.selectedVillageInfo).isEqualTo(null)
            val villageInfo = village1
            sellingViewModel.setVillageInfo(villageInfo)
            sellingViewModel.uiState.test {
                assertThat(awaitItem().selectedVillageInfo).isEqualTo(villageInfo)
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun verifyCommodityInfoAfterSearch() {
        runTest {
            assertThat(sellingViewModel.uiState.value.selectedCommodityInfo).isEqualTo(null)
            val sellingCommodityInfo = village1.sellingCommoditiesList.first()
            sellingViewModel.setCommodityInfo(sellingCommodityInfo)
            sellingViewModel.uiState.test {
                assertThat(awaitItem().selectedCommodityInfo).isEqualTo(sellingCommodityInfo)
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun verifyGrossValueCalculatedWhenAllDataSet() {
        runTest {
            assertThat(sellingViewModel.uiState.value.grossValue).isEqualTo(null)

            sellingViewModel.setSellerInfo(allSellers.first())
            sellingViewModel.uiState.test {
                assertThat(awaitItem().grossValue).isEqualTo(null)
                cancelAndIgnoreRemainingEvents()
            }

            sellingViewModel.setVillageInfo(village1)
            sellingViewModel.uiState.test {
                assertThat(awaitItem().grossValue).isEqualTo(null)
                cancelAndIgnoreRemainingEvents()
            }

            sellingViewModel.setCommodityInfo(village1.sellingCommoditiesList.first())
            sellingViewModel.uiState.test {
                assertThat(awaitItem().grossValue).isEqualTo(null)
                cancelAndIgnoreRemainingEvents()
            }

            sellingViewModel.event(SellingScreenEvent.OnGrossWtUpdate(TextFieldValue("5")))
            sellingViewModel.uiState.test {
                assertThat(awaitItem().isLoading).isTrue()
                cancelAndIgnoreRemainingEvents()
            }
            delay(500)
            sellingViewModel.uiState.test {
                val screenState = awaitItem()
                assertThat(screenState.grossValue).isNotEqualTo(null)
                assertThat(screenState.isLoading).isFalse()
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun verifyGrossValueCalculationForRegisteredSellerWithCommodityMeasurementTypeKg() {
        runTest {
            val seller = Seller(
                "abc",
                "Ajay",
                SellerRegistrationInfo.Register(loyalityCardId = "Lc1123")
            )
            val sellingCommodityInfo = SellingCommodityInfo(
                CommodityDetail(
                    "apple",
                    "Apple",
                    CommodityType.Fruit,
                    CommodityMeasurementType.Killogram
                ), 200.0f
            )
            VillageInfo(
                "vil1",
                name = "VillageAb",
                postalCode = "",
                sellingCommoditiesList = listOf(
                    sellingCommodityInfo
                )
            )
            val grossWt = "5"
            sellingViewModel.setSellerInfo(seller)
            sellingViewModel.setVillageInfo(village1)
            sellingViewModel.setCommodityInfo(sellingCommodityInfo)
            sellingViewModel.event(SellingScreenEvent.OnGrossWtUpdate(TextFieldValue(grossWt)))
            val expectedValue: Float = getExpectedGrossValue(
                grossWt = grossWt.toFloat(),
                pricePerMeasurementType = sellingCommodityInfo.pricePerMeasurementType,
                loyalityIndexValue = seller.sellerRegistrationInfo.getLoyalityIndexValue(),
                measurementType = sellingCommodityInfo.commodityDetail.commodityMeasurementType
            )
            delay(500)
            sellingViewModel.uiState.test {
                assertThat(awaitItem().grossValue).isEqualTo(expectedValue)
            }
        }
    }

    @Test
    fun verifyGrossValueCalculationForUnRegisteredSellerWithCommodityMeasurementTypeKg() {
        runTest {
            val seller = Seller(
                "abc",
                "Ajay",
                SellerRegistrationInfo.Unregister()
            )
            val sellingCommodityInfo = SellingCommodityInfo(
                CommodityDetail(
                    "apple",
                    "Apple",
                    CommodityType.Fruit,
                    CommodityMeasurementType.Killogram
                ), 200.0f
            )
            VillageInfo(
                "vil1",
                name = "VillageAb",
                postalCode = "",
                sellingCommoditiesList = listOf(
                    sellingCommodityInfo
                )
            )
            val grossWt = "5"
            sellingViewModel.setSellerInfo(seller)
            sellingViewModel.setVillageInfo(village1)
            sellingViewModel.setCommodityInfo(sellingCommodityInfo)
            sellingViewModel.event(SellingScreenEvent.OnGrossWtUpdate(TextFieldValue(grossWt)))
            val expectedValue: Float = getExpectedGrossValue(
                grossWt = grossWt.toFloat(),
                pricePerMeasurementType = sellingCommodityInfo.pricePerMeasurementType,
                loyalityIndexValue = seller.sellerRegistrationInfo.getLoyalityIndexValue(),
                measurementType = sellingCommodityInfo.commodityDetail.commodityMeasurementType
            )
            delay(500)
            sellingViewModel.uiState.test {
                assertThat(awaitItem().grossValue).isEqualTo(expectedValue)
            }
        }
    }

    @Test
    fun verifyGrossValueCalculationForRegisteredSellerWithCommodityMeasurementTypeDozen() {
        runTest {
            val seller = Seller(
                "abc",
                "Ajay",
                SellerRegistrationInfo.Register(loyalityCardId = "Lc1123")
            )
            val sellingCommodityInfo = SellingCommodityInfo(
                CommodityDetail(
                    "apple",
                    "Apple",
                    CommodityType.Fruit,
                    CommodityMeasurementType.Dozen
                ), 200.0f
            )
            VillageInfo(
                "vil1",
                name = "VillageAb",
                postalCode = "",
                sellingCommoditiesList = listOf(
                    sellingCommodityInfo
                )
            )
            val grossWt = "5"
            sellingViewModel.setSellerInfo(seller)
            sellingViewModel.setVillageInfo(village1)
            sellingViewModel.setCommodityInfo(sellingCommodityInfo)
            sellingViewModel.event(SellingScreenEvent.OnGrossWtUpdate(TextFieldValue(grossWt)))
            val expectedValue: Float = getExpectedGrossValue(
                grossWt = grossWt.toFloat(),
                pricePerMeasurementType = sellingCommodityInfo.pricePerMeasurementType,
                loyalityIndexValue = seller.sellerRegistrationInfo.getLoyalityIndexValue(),
                measurementType = sellingCommodityInfo.commodityDetail.commodityMeasurementType
            )
            delay(500)
            sellingViewModel.uiState.test {
                assertThat(awaitItem().grossValue).isEqualTo(expectedValue)
            }
        }
    }

    @Test
    fun verifyGrossValueCalculationForUnRegisteredSellerWithCommodityMeasurementTypeDozen() {
        runTest {
            val seller = Seller(
                "abc",
                "Ajay",
                SellerRegistrationInfo.Unregister()
            )
            val sellingCommodityInfo = SellingCommodityInfo(
                CommodityDetail(
                    "apple",
                    "Apple",
                    CommodityType.Fruit,
                    CommodityMeasurementType.Dozen
                ), 200.0f
            )
            VillageInfo(
                "vil1",
                name = "VillageAb",
                postalCode = "",
                sellingCommoditiesList = listOf(
                    sellingCommodityInfo
                )
            )
            val grossWt = "5"
            sellingViewModel.setSellerInfo(seller)
            sellingViewModel.setVillageInfo(village1)
            sellingViewModel.setCommodityInfo(sellingCommodityInfo)
            sellingViewModel.event(SellingScreenEvent.OnGrossWtUpdate(TextFieldValue(grossWt)))
            val expectedValue: Float = getExpectedGrossValue(
                grossWt = grossWt.toFloat(),
                pricePerMeasurementType = sellingCommodityInfo.pricePerMeasurementType,
                loyalityIndexValue = seller.sellerRegistrationInfo.getLoyalityIndexValue(),
                measurementType = sellingCommodityInfo.commodityDetail.commodityMeasurementType
            )
            delay(500)
            sellingViewModel.uiState.test {
                assertThat(awaitItem().grossValue).isEqualTo(expectedValue)
            }
        }
    }

    private fun getExpectedGrossValue(grossWt: Float, pricePerMeasurementType: Float, loyalityIndexValue: Float, measurementType: CommodityMeasurementType): Float {
        val valueWithoutIndex = when(measurementType) {
            CommodityMeasurementType.Dozen ->  pricePerMeasurementType * grossWt
            CommodityMeasurementType.Killogram ->  pricePerMeasurementType * grossWt * 1000
        }
        return valueWithoutIndex + (loyalityIndexValue * valueWithoutIndex)/ 100
    }
}