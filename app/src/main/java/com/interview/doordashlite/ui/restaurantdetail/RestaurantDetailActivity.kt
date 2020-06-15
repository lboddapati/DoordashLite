package com.interview.doordashlite.ui.restaurantdetail

import android.os.Bundle
import com.interview.doordashlite.R
import com.interview.doordashlite.base.BaseActivity
import com.interview.doordashlite.models.RestaurantFull
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

/**
 * Activity for displaying a detailed view of a single Restaurant
 */
class RestaurantDetailActivity: BaseActivity(), RestaurantDetailContract.View {

    private val presenter: RestaurantDetailContract.Presenter by inject {
        parametersOf(this, RestaurantDetailRouter.createViewModel(intent), lifecycle)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onCreate()
    }

    override fun displayRestaurant(restaurant: RestaurantFull) {
        title = restaurant.name
        setContentView(R.layout.restaurant_detail_activity)
    }

    override fun displayError() = displayError { presenter.onRetryClicked() }
}