package com.interview.doordashlite.ui.restaurantdetail

import android.content.Context
import android.content.Intent

object RestaurantDetailRouter: RestaurantDetailContract.Router {

    private const val EXTRA_RESTAURANT_ID = "restaurant_id"

    fun intentFor(context: Context, restaurantId: String) =
        Intent(context, RestaurantDetailActivity::class.java)
        .putExtra(EXTRA_RESTAURANT_ID, restaurantId)

    fun createViewModel(intent: Intent) = RestaurantDetailViewModel(
        intent.getStringExtra(EXTRA_RESTAURANT_ID) ?: ""
    )
}