package com.mandi.data.commodity.impl

import com.mandi.data.Result
import com.mandi.data.commodity.CommodityRepository
import com.mandi.model.CommodityDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FakeCommodityRepository: CommodityRepository {
    override suspend fun getAllCommodities(): Result<List<CommodityDetail>> =
        withContext(Dispatchers.IO) {
            Result.Success(commodityList)
        }
    }
