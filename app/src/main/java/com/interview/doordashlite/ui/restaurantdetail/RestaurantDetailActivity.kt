package com.interview.doordashlite.ui.restaurantdetail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.interview.doordashlite.models.RestaurantFull
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class RestaurantDetailActivity: AppCompatActivity(), RestaurantDetailContract.View {

    private val presenter: RestaurantDetailContract.Presenter by inject {
        parametersOf(this, RestaurantDetailRouter.createViewModel(intent), lifecycle)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onCreate()
    }

    override fun displayRestaurant(restaurant: RestaurantFull) {
        title = restaurant.name
    }

    override fun displayError(error: Throwable) {
        // TODO: Display more useful error message and hook up retry logic
        Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
            .show()
    }
}