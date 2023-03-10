package com.mandi.data

import com.mandi.data.commodity.CommodityRepository
import com.mandi.data.commodity.impl.FakeCommodityRepository
import com.mandi.data.seller.SellerRepository
import com.mandi.data.seller.impl.FakeSellerRepository
import com.mandi.data.village.VillageRepository
import com.mandi.data.village.impl.FakeVillageRepository
import javax.inject.Inject

interface AppContainer {
     val sellerRepository: SellerRepository
     val villageRepository: VillageRepository
     val commodityRepository: CommodityRepository
}

class AppContainerImpl @Inject internal constructor(
     override val sellerRepository: SellerRepository,
     override val villageRepository: VillageRepository,
     override val commodityRepository: CommodityRepository
): AppContainer