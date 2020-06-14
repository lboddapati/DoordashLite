package com.interview.doordashlite.ui.restaurantlist

import android.app.Activity
import com.interview.doordashlite.ui.restaurantdetail.RestaurantDetailRouter

class RestaurantListRouter(private val activity: Activity): RestaurantListContract.Router {
    override fun openRestaurantDetail(restaurantId: String) {
        activity.startActivity(RestaurantDetailRouter.intentFor(activity, restaurantId))
    }
}