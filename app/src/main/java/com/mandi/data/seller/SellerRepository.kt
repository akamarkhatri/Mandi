package com.mandi.data.seller

import com.mandi.model.Seller

interface SellerRepository {
    suspend fun getSellerById(loyalityCardId: String): com.mandi.data.Result<List<Seller>>
    suspend fun getSellerByName(name: String): com.mandi.data.Result<List<Seller>>
}