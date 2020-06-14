package com.interview.doordashlite.models

import com.squareup.moshi.Json

// TODO
data class RestaurantFull(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "cover_img_url") val thumbnailUrl: String,
    @field:Json(name = "status") val status: String,
    @field:Json(name = "delivery_fee") val deliveryFee: Int
)