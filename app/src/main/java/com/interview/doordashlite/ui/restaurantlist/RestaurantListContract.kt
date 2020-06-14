package com.interview.doordashlite.ui.restaurantlist

import com.interview.doordashlite.base.BasePresenter
import com.interview.doordashlite.models.RestaurantCondensed

interface RestaurantListContract {
    interface View {
        fun displayRestaurants(restaurants: List<RestaurantCondensed>)
        fun displayError(error: Throwable)
    }

    interface Presenter: BasePresenter {
        fun onRestaurantSelected(restaurant: RestaurantCondensed)
    }

    interface Router {
        fun openRestaurantDetail(restaurantId: String)
    }
}