package com.mandi.model

enum class CommodityType {
    Fruit,
    Village
}
enum class CommodityMeasurementType {
    Dozen,
    Killogram
}

data class CommodityDetail(
    val id: String,
    val name: String,
    val commodityType: CommodityType,
    val commodityMeasurementType: CommodityMeasurementType,
)