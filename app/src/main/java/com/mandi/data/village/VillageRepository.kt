package com.mandi.data.village

import com.mandi.model.VillageInfo

interface VillageRepository {
    suspend fun getAllVillage(): List<VillageInfo>

    suspend fun getVillage(id: String):VillageInfo?

    suspend fun getAllVillage(name: String): List<VillageInfo>
}