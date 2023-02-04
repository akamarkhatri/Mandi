package com.mandi.data.village

import com.mandi.data.Result
import com.mandi.model.SellingCommodityInfo
import com.mandi.model.VillageInfo

interface VillageRepository {
    suspend fun getAllVillage(): Result<List<VillageInfo>>

    suspend fun getVillage(id: String): Result<VillageInfo>

    suspend fun getAllVillage(name: String): Result<List<VillageInfo>>

    suspend fun getSellingCommodities(villageId: String, commodityName: String): Result<List<SellingCommodityInfo>>
}