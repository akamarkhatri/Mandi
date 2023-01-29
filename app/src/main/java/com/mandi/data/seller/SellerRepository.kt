package com.mandi.data.seller

import com.mandi.model.Seller

interface SellerRepository {
    suspend fun getSeller(name: String? = null, loyalityCardId: String? = null): Seller?
}