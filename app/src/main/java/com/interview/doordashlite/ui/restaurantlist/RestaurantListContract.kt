package com.interview.doordashlite.ui.restaurantlist

import android.location.Location
import com.interview.doordashlite.base.BasePresenter
import com.interview.doordashlite.models.ErrorType
import com.interview.doordashlite.models.RestaurantCondensed

interface RestaurantListContract {
    interface View {
        fun displayLoading()
        fun displayRestaurants(restaurants: List<RestaurantItemViewModel>, isFirstLoad: Boolean)
        fun displayEmptyState()
        fun displayError(errorType: ErrorType)
        fun checkAndGetLocation()
    }

    interface Presenter: BasePresenter {
        fun onLocationReceived(location: Location?)
        fun onRestaurantSelected(restaurant: RestaurantCondensed)
        fun onFavoriteClicked(restaurantModel: RestaurantItemViewModel)
        fun onRetryClicked(errorType: ErrorType)
        fun onScrolledToEnd()
    }

    interface Router {
        fun openRestaurantDetail(restaurantId: String)
    }
}