package com.mandi.util

import com.mandi.model.SellerRegistrationInfo
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import java.text.NumberFormat
import java.util.*

val numberFormat by lazy {
    NumberFormat.getInstance(Locale("en", "IN")).apply {
        maximumFractionDigits = 2
    }
}

private val sealedModule by lazy {
    SerializersModule {
        polymorphic(SellerRegistrationInfo::class, SellerRegistrationInfo.Register::class, SellerRegistrationInfo.Register.serializer())
        polymorphic(SellerRegistrationInfo::class, SellerRegistrationInfo.Unregister::class, SellerRegistrationInfo.Unregister.serializer())
    }
}
val json by lazy {
    Json {
        ignoreUnknownKeys = true
        serializersModule = sealedModule
        isLenient = true
        encodeDefaults = true
        coerceInputValues = true

    }
}
fun String?.checkAndConvertToNull() = if (this.equals("null", true)) null else this