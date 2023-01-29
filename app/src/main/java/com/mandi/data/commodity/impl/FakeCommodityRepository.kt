package com.mandi.data.commodity.impl

import com.mandi.data.commodity.CommodityRepository
import com.mandi.model.CommodityDetail

class FakeCommodityRepository: CommodityRepository {
    override suspend fun getAllCommodities(): List<CommodityDetail> {
        TODO("Not yet implemented")
    }
}