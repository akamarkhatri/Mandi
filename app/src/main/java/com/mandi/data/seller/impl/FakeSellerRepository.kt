package com.mandi.data.seller.impl

import com.mandi.data.Result
import com.mandi.data.seller.SellerRepository
import com.mandi.model.Seller
import com.mandi.model.SellerRegistrationInfo
import com.mandi.model.SellerRegistrationType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FakeSellerRepository : SellerRepository {
    override suspend fun getSeller(name: String?, loyalityCardId: String?): Result<List<Seller>> =
        withContext(Dispatchers.IO) {
            name?.let { name -> Result.Success(allSellers.filter { it.name.contains(name) }) }
                ?: loyalityCardId?.let{ cardId ->
                        allSellers.filter { it.sellerRegistrationInfo.type == SellerRegistrationType.Register }
                            .firstOrNull {
                                (it.sellerRegistrationInfo as SellerRegistrationInfo.Register).loyalityCardId == cardId
                            }?.let { Result.Success(listOf(it)) }
                }
                ?: Result.Error(IllegalArgumentException("Unable to find Seller"))
        }
}

private val allSellers: List<Seller> by lazy {
    listOf(
        Seller("Seller_1",
            name = "Ramesh",
            sellerRegistrationInfo = SellerRegistrationInfo.Register(loyalityCardId = "LCID_1")),
        Seller("Seller_2",
            name = "Ram",
            sellerRegistrationInfo = SellerRegistrationInfo.Register(loyalityCardId = "LCID_2")),
        Seller("Seller_3",
            name = "Ramu",
            sellerRegistrationInfo = SellerRegistrationInfo.Register(loyalityCardId = "LCID_3")),
        Seller("Seller_4",
            name = "Raj",
            sellerRegistrationInfo = SellerRegistrationInfo.Register(loyalityCardId = "LCID_4")),
        Seller("Seller_5",
            name = "Mahesh",
            sellerRegistrationInfo = SellerRegistrationInfo.Register(loyalityCardId = "LCID_5")),
        Seller("Seller_6",
            name = "Manoj",
            sellerRegistrationInfo = SellerRegistrationInfo.Unregister()),
        Seller("Seller_7",
            name = "Max",
            sellerRegistrationInfo = SellerRegistrationInfo.Unregister()),
        Seller("Seller_8",
            name = "Rocky",
            sellerRegistrationInfo = SellerRegistrationInfo.Unregister()),
        Seller("Seller_9",
            name = "Raju",
            sellerRegistrationInfo = SellerRegistrationInfo.Unregister()),
        Seller("Seller_10",
            name = "Dan",
            sellerRegistrationInfo = SellerRegistrationInfo.Unregister()),
    )
}