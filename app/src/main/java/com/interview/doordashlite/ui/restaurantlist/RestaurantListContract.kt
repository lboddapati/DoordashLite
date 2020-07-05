package com.interview.doordashlite.ui.restaurantlist

import com.interview.doordashlite.base.BasePresenter
import com.interview.doordashlite.models.RestaurantCondensed

interface RestaurantListContract {
    interface View {
        fun displayLoading()
        fun displayRestaurants(restaurants: List<RestaurantItemViewModel>)
        fun displayEmptyState()
        fun displayError()
    }

    interface Presenter: BasePresenter {
        fun onRestaurantSelected(restaurant: RestaurantCondensed)
        fun onFavoriteClicked(restaurantModel: RestaurantItemViewModel)
        fun onRetryClicked()
    }

    interface Router {
        fun openRestaurantDetail(restaurantId: String)
    }
}