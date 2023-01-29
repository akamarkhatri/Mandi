package com.mandi.data.village.impl

import com.mandi.data.Result
import com.mandi.data.village.VillageRepository
import com.mandi.model.VillageInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FakeVillageRepository: VillageRepository {
    override suspend fun getAllVillage(): Result<List<VillageInfo>> =
        withContext(Dispatchers.IO) {
            Result.Success(allVilages)
        }

    override suspend fun getAllVillage(name: String): Result<List<VillageInfo>> {
        return getAllVillage()
    }

    override suspend fun getVillage(id: String): Result<VillageInfo> {
        return Result.Success(allVilages.first())
    }
}