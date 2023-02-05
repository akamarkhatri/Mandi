package com.mandi.util

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.internal.Primitives
import com.mandi.model.SellerRegistrationInfo
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import java.lang.reflect.Type
import java.text.NumberFormat
import java.util.*
import kotlin.reflect.KClass

val numberFormat by lazy {
    NumberFormat.getInstance(Locale("en", "IN")).apply {
        maximumFractionDigits = 2
    }
}

fun Any?.getJson():String? = this?.let { Gson().toJson(it) }

fun <T> getFromJson(json: String?, clazz: Class<T>): T? {
    val type = json?.let {
        Gson().fromJson(it, clazz)
    }
    return type
}

/*
@Throws(JsonSyntaxException::class)
fun <T> fromJson(json: String?, classOfT: Class<T>?): T {
    val `object` = fromJson<Any>(json, classOfT as Type?)
    return Primitives.wrap(classOfT).cast(`object`)
}*/

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