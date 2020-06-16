package com.interview.doordashlite.ui.restaurantdetail

import android.content.res.Resources
import com.interview.doordashlite.R
import com.interview.doordashlite.models.RestaurantFull

object RestaurantUtils {
    private const val DETAIL_STRING_SEPARATOR = " | "

    fun getDetailsString(resources: Resources, restaurant: RestaurantFull) = listOf(
        restaurant.averageRating.toString(),
        resources.getQuantityString(
            R.plurals.num_ratings,
            restaurant.numberOfRatings,
            restaurant.numberOfRatings
        ),
        getPriceRangeString(resources, restaurant.priceRange) ?: "",
        resources.getString(R.string.delivery_fee_formatted, restaurant.deliveryFee),
        restaurant.status
    ).joinToString(DETAIL_STRING_SEPARATOR)

    private fun getPriceRangeString(
        resources: Resources,
        priceRange: Int
    ) = when (priceRange) {
        1 -> resources.getString(R.string.price_range_1)
        2 -> resources.getString(R.string.price_range_1)
        3 -> resources.getString(R.string.price_range_1)
        4 -> resources.getString(R.string.price_range_1)
        else -> null
    }
}