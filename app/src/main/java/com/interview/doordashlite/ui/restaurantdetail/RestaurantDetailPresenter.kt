package com.interview.doordashlite.ui.restaurantdetail

import android.annotation.SuppressLint
import com.interview.doordashlite.datalayer.DataRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject

class RestaurantDetailPresenter(
    private val view: RestaurantDetailContract.View,
    private val viewModel: RestaurantDetailViewModel
): RestaurantDetailContract.Presenter, KoinComponent {

    private val dataRepository : DataRepository by inject()

    override fun onCreate() {
        loadRestaurant()
    }

    @SuppressLint("CheckResult")
    private fun loadRestaurant() {
        dataRepository.getRestaurant(viewModel.restaurantId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { restaurant -> view.displayRestaurant(restaurant) },
                { error -> view.displayError(error) }
            )
    }
}

data class RestaurantDetailViewModel(
    val restaurantId: String
)