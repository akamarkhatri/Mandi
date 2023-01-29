package com.mandi.data.seller.impl

import com.mandi.data.seller.SellerRepository
import com.mandi.model.Seller

class FakeSellerRepository: SellerRepository {
    override suspend fun getSeller(name: String?, loyalityCardId: String?): Seller? {
        TODO("Not yet implemented")
    }
}