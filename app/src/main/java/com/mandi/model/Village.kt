package com.mandi.model
@kotlinx.serialization.Serializable
data class VillageInfo(
    val id: String,
    val postalCode: String,
    val name: String,
    val sellingCommoditiesList: List<SellingCommodityInfo>,
)

@kotlinx.serialization.Serializable
data class SellingCommodityInfo(val commodityDetail: CommodityDetail, val pricePerMeasurementType: Float)