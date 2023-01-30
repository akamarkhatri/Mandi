package com.mandi.data.village.impl

import com.mandi.data.commodity.impl.*
import com.mandi.model.SellingCommodityInfo
import com.mandi.model.VillageInfo

val village1 by lazy {
    VillageInfo(id = "Village_1",
        postalCode = "208012",
        name = "Ramnagar",
        sellingCommoditiesList = listOf(
            SellingCommodityInfo(commodityDetail = apple, pricePerMeasurementType = 200.1110333f ),
            SellingCommodityInfo(commodityDetail = orange, pricePerMeasurementType = 180f ),
            SellingCommodityInfo(commodityDetail = banana, pricePerMeasurementType = 60f )
        )
    )
}

val village2 by lazy {
    VillageInfo(id = "Village_2",
        postalCode = "205011",
        name = "Ramgarh",
        sellingCommoditiesList = listOf(
            SellingCommodityInfo(commodityDetail = apple, pricePerMeasurementType = 300.444454f ),
            SellingCommodityInfo(commodityDetail = orange, pricePerMeasurementType = 200f ),
            SellingCommodityInfo(commodityDetail = banana, pricePerMeasurementType = 100f ),
            SellingCommodityInfo(commodityDetail = tomato, pricePerMeasurementType = 20f ),
            SellingCommodityInfo(commodityDetail = potato, pricePerMeasurementType = 50f )
        )
    )
}

val allVilages by lazy {
    listOf(village1, village2)
}