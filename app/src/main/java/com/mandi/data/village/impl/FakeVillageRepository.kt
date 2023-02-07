package com.mandi.data.village.impl

import com.mandi.data.BaseRepository
import com.mandi.data.DispatcherProvider
import com.mandi.data.Result
import com.mandi.data.successOr
import com.mandi.data.village.VillageRepository
import com.mandi.model.SellingCommodityInfo
import com.mandi.model.VillageInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FakeVillageRepository(override val dispatcherProvider: DispatcherProvider): VillageRepository {
    override suspend fun getAllVillage(): Result<List<VillageInfo>> =
        withContext(dispatcherProvider.io) {
            Result.Success(allVilages)
        }

    override suspend fun getAllVillage(name: String): Result<List<VillageInfo>> =
        withContext(dispatcherProvider.io) {
            val filter = allVilages.filter { it.name.contains(name, true) }
            Result.Success(filter)
        }

    override suspend fun getVillage(id: String): Result<VillageInfo> {
        return Result.Success(allVilages.first())
    }

    override suspend fun getSellingCommodities(
        villageId: String,
        commodityName: String,
    ): Result<List<SellingCommodityInfo>> =
        withContext(dispatcherProvider.io) {
            val sellingCommodityInfos = allVilages.find { it.id == villageId }?.sellingCommoditiesList?.filter {
                it.commodityDetail.name.contains(
                    commodityName,
                    true
                )
            }?: listOf()
            Result.Success(sellingCommodityInfos)
        }
}