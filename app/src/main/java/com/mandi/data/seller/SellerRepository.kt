package com.mandi.data.seller

import com.mandi.data.BaseRepository
import com.mandi.model.Seller

interface SellerRepository: BaseRepository {
    suspend fun getSellerById(loyalityCardId: String): com.mandi.data.Result<List<Seller>>
    suspend fun getSellerByName(name: String): com.mandi.data.Result<List<Seller>>
}