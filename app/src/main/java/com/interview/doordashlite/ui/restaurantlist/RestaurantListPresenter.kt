package com.interview.doordashlite.ui.restaurantlist

import com.interview.doordashlite.base.LifecycleAwareSubscriptionManager
import com.interview.doordashlite.datalayer.DataRepository
import com.interview.doordashlite.models.RestaurantCondensed
import io.reactivex.observers.DisposableSingleObserver
import org.koin.core.KoinComponent
import org.koin.core.inject

class RestaurantListPresenter(
    private val view: RestaurantListContract.View,
    private val router: RestaurantListContract.Router,
    private val subscriptionManager: LifecycleAwareSubscriptionManager
): RestaurantListContract.Presenter, KoinComponent {

    private val dataRepository : DataRepository by inject()

    override fun onCreate() {
        loadRestaurants()
    }

    override fun onRestaurantSelected(restaurant: RestaurantCondensed) {
        router.openRestaurantDetail(restaurant.id)
    }

    private fun loadRestaurants() {
        subscriptionManager.subscribe(
            // TODO: get & use current location instead of hardcoding
            dataRepository.getRestaurantList(37.422740.toFloat(), (-122.139956).toFloat()),
            object: DisposableSingleObserver<List<RestaurantCondensed>>() {
                override fun onSuccess(restaurants: List<RestaurantCondensed>) {
                    // TODO:
                    // 1. Handle empty response
                    // 2. paginated loading for performance improvements
                    // 3. Loading state for perceived performance improvements
                    view.displayRestaurants(restaurants)
                }

                override fun onError(error: Throwable) {
                    view.displayError(error)
                }
            })
    }
}