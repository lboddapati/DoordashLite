package com.interview.doordashlite.ui.restaurantlist

import android.annotation.SuppressLint
import com.interview.doordashlite.datalayer.DataRepository
import com.interview.doordashlite.models.RestaurantCondensed
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject

class RestaurantListPresenter(
    private val view: RestaurantListContract.View,
    private val router: RestaurantListContract.Router
): RestaurantListContract.Presenter, KoinComponent {

    private val dataRepository : DataRepository by inject()

    override fun onCreate() {
        loadRestaurants()
    }

    override fun onRestaurantSelected(restaurant: RestaurantCondensed) {
        router.openRestaurantDetail(restaurant.id)
    }

    @SuppressLint("CheckResult")
    private fun loadRestaurants() {
        // TODO: get & use current location instead of hardcoding
        dataRepository.getRestaurantList(37.422740.toFloat(), (-122.139956).toFloat())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { restaurants -> view.displayRestaurants(restaurants) },
                { error -> view.displayError(error) }
            )
    }
}