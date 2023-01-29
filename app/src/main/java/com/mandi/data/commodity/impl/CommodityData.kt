package com.mandi.data.commodity.impl

import com.mandi.model.CommodityDetail
import com.mandi.model.CommodityMeasurementType
import com.mandi.model.CommodityType

val commodityList: List<CommodityDetail> by lazy {
    listOf(
        apple,
        orange,
        banana,
        potato,
        tomato
    )
}

val apple by lazy {
    CommodityDetail(
        id = "apple",
        name = "Apple",
        commodityType = CommodityType.Fruit,
        commodityMeasurementType = CommodityMeasurementType.Killogram
    )
}
val orange by lazy {
    CommodityDetail(
        id = "orange",
        name = "Orange",
        commodityType = CommodityType.Fruit,
        commodityMeasurementType = CommodityMeasurementType.Killogram
    )
}
val banana by lazy {
    CommodityDetail(
        id = "banana",
        name = "Banana",
        commodityType = CommodityType.Fruit,
        commodityMeasurementType = CommodityMeasurementType.Dozen
    )
}

val potato by lazy {
    CommodityDetail(
        id = "potato",
        name = "Potato",
        commodityType = CommodityType.Vegetable,
        commodityMeasurementType = CommodityMeasurementType.Killogram
    )
}

val tomato by lazy {
    CommodityDetail(
        id = "tomato",
        name = "Tomato",
        commodityType = CommodityType.Vegetable,
        commodityMeasurementType = CommodityMeasurementType.Killogram
    )
}