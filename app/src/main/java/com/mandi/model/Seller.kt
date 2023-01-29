package com.mandi.model

data class Seller(val id: String, val name: String, val sellerRegistrationInfo: SellerRegistrationInfo)
enum class SellerRegistrationType {
    Register,
    UnRegister
}

sealed class SellerRegistrationInfo(val type: SellerRegistrationType, val loyalityIndex: Float){
    class Register(loyalityIndex: Float = 1.12f, val loyalityCardId: String) : SellerRegistrationInfo(SellerRegistrationType.Register, loyalityIndex)
    class Unregister(loyalityIndex: Float = 0.98f) : SellerRegistrationInfo(SellerRegistrationType.UnRegister, loyalityIndex)
}