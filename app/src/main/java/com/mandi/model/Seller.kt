package com.mandi.model

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class Seller(val id: String, val name: String, @Polymorphic val sellerRegistrationInfo: SellerRegistrationInfo) {
    fun getLoyalityCardId():String {
        return when(sellerRegistrationInfo) {
            is SellerRegistrationInfo.Register -> sellerRegistrationInfo.loyalityCardId
            is SellerRegistrationInfo.Unregister -> ""
        }
    }
}
enum class SellerRegistrationType {
    Register,
    UnRegister
}

@kotlinx.serialization.Serializable
sealed class SellerRegistrationInfo(
    @SerialName("registrationType")
    val registrationType: SellerRegistrationType){

    @kotlinx.serialization.Serializable
    data class Register(val loyalityIndex: Float = 1.12f, val loyalityCardId: String) : SellerRegistrationInfo(SellerRegistrationType.Register)
    @kotlinx.serialization.Serializable
    data class Unregister(val loyalityIndex: Float = 0.98f) : SellerRegistrationInfo(SellerRegistrationType.UnRegister);

    fun getLoyalityIndexValue() = when(this) {
        is Register -> loyalityIndex
        is Unregister -> loyalityIndex
    }

    fun getCardId() = when(this) {
        is Register -> loyalityCardId
        is Unregister -> ""
    }
}
