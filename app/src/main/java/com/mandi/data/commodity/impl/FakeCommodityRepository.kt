package com.mandi.data.commodity.impl

import com.mandi.data.BaseRepository
import com.mandi.data.DispatcherProvider
import com.mandi.data.Result
import com.mandi.data.commodity.CommodityRepository
import com.mandi.model.CommodityDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FakeCommodityRepository(override val dispatcherProvider: DispatcherProvider): CommodityRepository {
    override suspend fun getAllCommodities(): Result<List<CommodityDetail>> =
        withContext(dispatcherProvider.io) {
            Result.Success(commodityList)
        }
    }
