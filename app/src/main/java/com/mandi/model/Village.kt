package com.mandi.model

data class VillageInfo(val id: String, val postalCode: String, val name: String)

data class SellingCommodityInfo(val commodityDetail: CommodityDetail, val pricePerMeasurementType: Float)