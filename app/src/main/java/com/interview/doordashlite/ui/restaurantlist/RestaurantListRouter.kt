package com.interview.doordashlite.ui.restaurantlist

import android.app.Activity
import android.content.Intent
import com.interview.doordashlite.ui.login.LoginActivity
import com.interview.doordashlite.ui.restaurantdetail.RestaurantDetailRouter

class RestaurantListRouter(private val activity: Activity): RestaurantListContract.Router {

    override fun openRestaurantDetail(restaurantId: String) {
        activity.startActivity(RestaurantDetailRouter.intentFor(activity, restaurantId))
    }

    override fun startLoginActivity() {
        activity.startActivity(Intent(activity, LoginActivity::class.java))
        activity.finish()
    }
}