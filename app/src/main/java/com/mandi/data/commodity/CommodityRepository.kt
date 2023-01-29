package com.mandi.data.commodity

import com.mandi.model.CommodityDetail

interface CommodityRepository {
    suspend fun getAllCommodities(): List<CommodityDetail>
}