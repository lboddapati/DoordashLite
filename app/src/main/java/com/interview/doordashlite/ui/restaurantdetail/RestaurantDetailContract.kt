package com.interview.doordashlite.ui.restaurantdetail

import com.interview.doordashlite.base.BasePresenter
import com.interview.doordashlite.models.MenuCondensed
import com.interview.doordashlite.models.RestaurantFull

interface RestaurantDetailContract {
    interface View {
        fun displayLoading()
        fun displayRestaurant(restaurant: RestaurantFull)
        fun displayError()
    }

    interface Presenter: BasePresenter {
        fun onRetryClicked()
        fun onMenuSelected(menu: MenuCondensed)
    }

    interface Router {}
}