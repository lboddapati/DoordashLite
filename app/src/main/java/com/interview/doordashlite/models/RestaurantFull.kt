package com.interview.doordashlite.models

import com.squareup.moshi.Json

data class RestaurantFull(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "description") val description: String,
    @field:Json(name = "cover_img_url") val coverImageUrl: String?,
    @field:Json(name = "average_rating") val averageRating: Double,
    @field:Json(name = "number_of_ratings") val numberOfRatings: Int,
    @field:Json(name = "price_range") val priceRange: Int,
    @field:Json(name = "delivery_fee") val deliveryFee: Double,
    @field:Json(name = "status") val status: String,
    @field:Json(name = "menus") val menus: List<MenuCondensed>
)