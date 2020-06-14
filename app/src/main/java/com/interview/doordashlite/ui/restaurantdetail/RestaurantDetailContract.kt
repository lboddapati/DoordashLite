package com.interview.doordashlite.ui.restaurantdetail

import com.interview.doordashlite.base.BasePresenter
import com.interview.doordashlite.models.RestaurantFull

interface RestaurantDetailContract {
    interface View {
        fun displayRestaurant(restaurant: RestaurantFull)
        fun displayError(error: Throwable)
    }

    interface Presenter: BasePresenter {}

    interface Router {}
}