package com.mandi.data

import com.mandi.data.commodity.CommodityRepository
import com.mandi.data.commodity.impl.FakeCommodityRepository
import com.mandi.data.seller.SellerRepository
import com.mandi.data.seller.impl.FakeSellerRepository
import com.mandi.data.village.VillageRepository
import com.mandi.data.village.impl.FakeVillageRepository

interface AppContainer {
    val sellerRepository: SellerRepository
    val villageRepository: VillageRepository
    val commodityRepository: CommodityRepository
}

class FakeAppContainer: AppContainer {
    override val sellerRepository: SellerRepository by lazy {
        FakeSellerRepository()
    }

    override val villageRepository: VillageRepository by lazy {
        FakeVillageRepository()
    }

    override val commodityRepository: CommodityRepository by lazy {
        FakeCommodityRepository()
    }

}