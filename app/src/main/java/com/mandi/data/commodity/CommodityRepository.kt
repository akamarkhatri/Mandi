package com.mandi.data.commodity

import com.mandi.data.Result
import com.mandi.model.CommodityDetail

interface CommodityRepository {
    suspend fun getAllCommodities(): Result<List<CommodityDetail>>
}
