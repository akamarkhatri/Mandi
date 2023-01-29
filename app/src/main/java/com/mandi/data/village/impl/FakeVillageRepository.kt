package com.mandi.data.village.impl

import com.mandi.data.village.VillageRepository
import com.mandi.model.VillageInfo

class FakeVillageRepository: VillageRepository {
    override suspend fun getAllVillage(): List<VillageInfo> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllVillage(name: String): List<VillageInfo> {
        TODO("Not yet implemented")
    }

    override suspend fun getVillage(id: String): VillageInfo? {
        TODO("Not yet implemented")
    }
}