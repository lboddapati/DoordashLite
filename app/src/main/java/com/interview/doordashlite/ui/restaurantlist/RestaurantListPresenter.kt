package com.interview.doordashlite.ui.restaurantlist

import android.content.SharedPreferences
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.interview.doordashlite.base.LifecycleAwareSubscriptionManager
import com.interview.doordashlite.datalayer.DataRepository
import com.interview.doordashlite.datalayer.LOGIN_TOKEN_KEY
import com.interview.doordashlite.models.RestaurantCondensed
import io.reactivex.Single
import io.reactivex.functions.BiFunction
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
    private val sharedPreferences: SharedPreferences by inject()

    override fun onRestaurantSelected(restaurant: RestaurantCondensed) {
        router.openRestaurantDetail(restaurant.id)
    }

    override fun onRetryClicked() {
        loadRestaurants()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun onCreate() {
        val isLoggedIn = sharedPreferences.getString(LOGIN_TOKEN_KEY, null) != null
        if (isLoggedIn) {
            loadRestaurants()
        } else {
            router.startLoginActivity()
        }
    }
    private fun loadRestaurants() {
        view.displayLoading()
        subscriptionManager.subscribe(
            // TODO: get & use current location instead of hardcoding
            Single.zip(
                dataRepository.getRestaurantList(viewModel.latitude, viewModel.longitude),
                dataRepository.getFavoriteRestaurants(),
                BiFunction<List<RestaurantCondensed>, Set<String>, List<RestaurantItemViewModel>> {
                        restaurants, favorites -> convertRestaurantsToModels(restaurants, favorites)
                }
            ),
            object: DisposableSingleObserver<List<RestaurantItemViewModel>>() {
                override fun onSuccess(restaurants: List<RestaurantItemViewModel>) {
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

    override fun onFavoriteClicked(restaurantModel: RestaurantItemViewModel) {
        if (restaurantModel.isFavorite) {
            subscriptionManager.subscribe(dataRepository.removeFavoriteRestaurant(restaurantModel.restaurant.id))
        } else {
            subscriptionManager.subscribe(dataRepository.addFavoriteRestaurant(restaurantModel.restaurant.id))
        }
    }

    private fun convertRestaurantsToModels(
        restaurants: List<RestaurantCondensed>,
        favorites: Set<String>
    ): List<RestaurantItemViewModel> = restaurants.map { restaurant ->
        RestaurantItemViewModel(restaurant, favorites.contains(restaurant.id))
    }
}

data class RestaurantListViewModel(
    var latitude: Float = 37.422740.toFloat(),
    var longitude: Float = (-122.139956).toFloat()
)

data class RestaurantItemViewModel(
    val restaurant: RestaurantCondensed,
    var isFavorite: Boolean
)