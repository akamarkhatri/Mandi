package com.mandi

import com.mandi.data.AppContainer
import com.mandi.data.DispatcherProvider
import com.mandi.data.commodity.CommodityRepository
import com.mandi.data.commodity.impl.FakeCommodityRepository
import com.mandi.data.seller.SellerRepository
import com.mandi.data.seller.impl.FakeSellerRepository
import com.mandi.data.village.VillageRepository
import com.mandi.data.village.impl.FakeVillageRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseTest {
    private val testDispatcher by lazy {
        UnconfinedTestDispatcher()
    }
    init {
        Dispatchers.setMain(testDispatcher)
    }
    val appContainer by lazy {
        object : AppContainer {
            override val sellerRepository: SellerRepository by lazy {
                    FakeSellerRepository(testDispatcherProvider)
            }
            override val villageRepository: VillageRepository by lazy {
                FakeVillageRepository(testDispatcherProvider)
            }
            override val commodityRepository: CommodityRepository by lazy {
                FakeCommodityRepository(testDispatcherProvider)
            }
        }
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcherProvider by lazy {
        object : DispatcherProvider {

            override val main: CoroutineDispatcher
                get() = testDispatcher
            override val io: CoroutineDispatcher
                get() = testDispatcher
            override val default: CoroutineDispatcher
                get() = testDispatcher

        }
    }
}