package com.interview.doordashlite.ui.restaurantdetail

import android.annotation.SuppressLint
import com.interview.doordashlite.base.LifecycleAwareSubscriptionManager
import com.interview.doordashlite.datalayer.DataRepository
import com.interview.doordashlite.models.RestaurantCondensed
import com.interview.doordashlite.models.RestaurantFull
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import org.koin.core.KoinComponent
import org.koin.core.inject

class RestaurantDetailPresenter(
    private val view: RestaurantDetailContract.View,
    private val viewModel: RestaurantDetailViewModel,
    private val subscriptionManager: LifecycleAwareSubscriptionManager
): RestaurantDetailContract.Presenter, KoinComponent {

    private val dataRepository: DataRepository by inject()

    override fun onCreate() {
        loadRestaurant()
    }

    override fun onRetryClicked() {
        loadRestaurant()
    }

    private fun loadRestaurant() {
        view.displayLoading()
        subscriptionManager.subscribe(
            dataRepository.getRestaurant(viewModel.restaurantId),
            object: DisposableSingleObserver<RestaurantFull>() {
                override fun onSuccess(restaurant: RestaurantFull) {
                    view.displayRestaurant(restaurant)
                }

                override fun onError(error: Throwable) {
                    view.displayError()
                }
            })
    }
}

data class RestaurantDetailViewModel(
    val restaurantId: String
)