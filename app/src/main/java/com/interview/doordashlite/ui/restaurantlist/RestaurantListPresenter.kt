package com.interview.doordashlite.ui.restaurantlist

import com.interview.doordashlite.base.LifecycleAwareSubscriptionManager
import com.interview.doordashlite.datalayer.DataRepository
import com.interview.doordashlite.models.RestaurantCondensed
import io.reactivex.observers.DisposableSingleObserver
import org.koin.core.KoinComponent
import org.koin.core.inject

class RestaurantListPresenter(
    private val view: RestaurantListContract.View,
    private val viewModel: RestaurantListViewModel,
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

    override fun onRetryClicked() {
        loadRestaurants()
    }

    private fun loadRestaurants() {
        view.displayLoading()
        subscriptionManager.subscribe(
            // TODO: get & use current location instead of hardcoding
            dataRepository.getRestaurantList(viewModel.latitude, viewModel.longitude),
            object: DisposableSingleObserver<List<RestaurantCondensed>>() {
                override fun onSuccess(restaurants: List<RestaurantCondensed>) {
                    // TODO: paginated loading, performance improvements
                    if (restaurants.isEmpty()) {
                        view.displayEmptyState()
                    } else {
                        view.displayRestaurants(restaurants)
                    }
                }

                override fun onError(error: Throwable) {
                    view.displayError()
                }
            })
    }
}

data class RestaurantListViewModel(
    var latitude: Float = 37.422740.toFloat(),
    var longitude: Float = (-122.139956).toFloat()
)