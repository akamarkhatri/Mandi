package com.mandi.data.seller.impl

import com.mandi.data.BaseRepository
import com.mandi.data.DispatcherProvider
import com.mandi.data.Result
import com.mandi.data.seller.SellerRepository
import com.mandi.model.Seller
import com.mandi.model.SellerRegistrationInfo
import com.mandi.model.SellerRegistrationType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FakeSellerRepository(override val dispatcherProvider: DispatcherProvider) : SellerRepository {

    override suspend fun getSellerById(loyalityCardId: String): Result<List<Seller>> =
        withContext(dispatcherProvider.io) {
            val sellerList = allSellers.filter { it.getLoyalityCardId().contains(loyalityCardId,true) }
            if (sellerList.isEmpty()) {
                null
            } else {
                Result.Success(sellerList)
            }
        } ?: Result.Error(IllegalArgumentException("Unable to find Seller"))

    override suspend fun getSellerByName(name: String): Result<List<Seller>> =
        withContext(dispatcherProvider.io) {
                val sellerList = allSellers.filter { it.name.contains(name,true) }
                if (sellerList.isEmpty()) {
                    null
                } else {
                    Result.Success(sellerList)
                }
            } ?: Result.Error(IllegalArgumentException("Unable to find Seller"))
}