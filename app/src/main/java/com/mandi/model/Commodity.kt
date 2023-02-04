package com.mandi.model

import com.mandi.R

enum class CommodityType {
    Fruit,
    Vegetable
}
enum class CommodityMeasurementType {
    Dozen,
    Killogram;

    fun getMesurementTypeNameResId(): Int {
        return when (this) {
            Dozen -> R.string.dozens
            Killogram -> R.string.tonnes
        }
    }
}

@kotlinx.serialization.Serializable
data class CommodityDetail(
    val id: String,
    val name: String,
    val commodityType: CommodityType,
    val commodityMeasurementType: CommodityMeasurementType,
)