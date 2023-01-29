package com.mandi.model

data class VillageInfo(
    val id: String,
    val postalCode: String,
    val name: String,
    val sellingCommoditiesList: List<SellingCommodityInfo>,
)

data class SellingCommodityInfo(val commodityDetail: CommodityDetail, val pricePerMeasurementType: Float)