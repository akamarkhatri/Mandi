package com.mandi.data.commodity

import com.mandi.data.BaseRepository
import com.mandi.data.DispatcherProvider
import com.mandi.data.Result
import com.mandi.model.CommodityDetail

interface CommodityRepository: BaseRepository {
    suspend fun getAllCommodities(): Result<List<CommodityDetail>>
}
