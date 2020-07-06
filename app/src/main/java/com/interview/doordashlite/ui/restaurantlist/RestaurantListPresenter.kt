package com.interview.doordashlite.ui.restaurantlist

import android.location.Location
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.interview.doordashlite.base.LifecycleAwareSubscriptionManager
import com.interview.doordashlite.datalayer.DataRepository
import com.interview.doordashlite.models.ErrorType
import com.interview.doordashlite.models.RestaurantCondensed
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.observers.DisposableSingleObserver
import org.koin.core.KoinComponent
import org.koin.core.inject

private const val DEFAULT_LIMIT = 20

class RestaurantListPresenter(
    private val view: RestaurantListContract.View,
    private val viewModel: RestaurantListViewModel,
    private val router: RestaurantListContract.Router,
    private val subscriptionManager: LifecycleAwareSubscriptionManager
): RestaurantListContract.Presenter, KoinComponent {

    private val dataRepository : DataRepository by inject()

    override fun onSaveInstanceState(saveState: Bundle) {
        viewModel.saveStateToBundle(saveState)
    }

    override fun onLocationReceived(location: Location?) {
        if (location == null) {
            view.displayError(ErrorType.NO_LOCATION)
            return
        }
        viewModel.location = location
        loadRestaurants()
    }

    override fun onRestaurantSelected(restaurant: RestaurantCondensed) {
        router.openRestaurantDetail(restaurant.id)
    }

    override fun onRetryClicked(errorType: ErrorType) {
        when (errorType) {
            ErrorType.GENERIC -> loadRestaurants()
            ErrorType.NO_LOCATION, ErrorType.LOCATION_PERMISSION_DENIED ->
                view.checkAndGetLocation()
        }
    }

    override fun onFavoriteClicked(restaurantModel: RestaurantItemViewModel) {
        if (restaurantModel.isFavorite) {
            subscriptionManager.subscribe(dataRepository.removeFavoriteRestaurant(restaurantModel.restaurant.id))
        } else {
            subscriptionManager.subscribe(dataRepository.addFavoriteRestaurant(restaurantModel.restaurant.id))
        }
    }

    override fun onScrolledToEnd() {
        if (viewModel.canLoadMore) {
            loadRestaurants()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun loadRestaurants() {
        if (viewModel.isLoading) return

        if (viewModel.location == null) {
            view.checkAndGetLocation()
            return
        }

        val isFirstLoad = viewModel.offset == 0
        if (isFirstLoad) view.displayLoading()
        viewModel.isLoading = true

        subscriptionManager.subscribe(
            Single.zip(
                dataRepository.getRestaurantList(
                    viewModel.location?.latitude ?: 0.0,
                    viewModel.location?.longitude ?: 0.0,
                    DEFAULT_LIMIT,
                    viewModel.offset
                ),
                dataRepository.getFavoriteRestaurants(),
                BiFunction<List<RestaurantCondensed>, Set<String>, List<RestaurantItemViewModel>> {
                        restaurants, favorites -> convertRestaurantsToModels(restaurants, favorites)
                }
            ),
            object: DisposableSingleObserver<List<RestaurantItemViewModel>>() {
                override fun onSuccess(restaurants: List<RestaurantItemViewModel>) {
                    if (restaurants.isEmpty() && isFirstLoad) {
                        view.displayEmptyState()
                    } else {
                        view.displayRestaurants(restaurants, isFirstLoad)
                    }

                    viewModel.offset = restaurants.size
                    viewModel.canLoadMore = restaurants.size >= DEFAULT_LIMIT - 1
                    viewModel.isLoading = false
                }

                override fun onError(error: Throwable) {
                    if (isFirstLoad) view.displayError(ErrorType.GENERIC)
                    viewModel.isLoading = false
                }
            })
    }

    private fun convertRestaurantsToModels(
        restaurants: List<RestaurantCondensed>,
        favorites: Set<String>
    ): List<RestaurantItemViewModel> = restaurants.map { restaurant ->
        RestaurantItemViewModel(restaurant, favorites.contains(restaurant.id))
    }
}

data class RestaurantListViewModel(
    var location: Location? = null,
    var offset: Int = 0,
    var canLoadMore: Boolean = true,
    var isLoading: Boolean = false
) {
    fun saveStateToBundle(bundle: Bundle) {
        bundle.putParcelable(LOCATION_KEY, location)
    }

    companion object {
        private const val LOCATION_KEY = "location_key"
        fun fromBundle(bundle: Bundle) = RestaurantListViewModel(
            bundle.getParcelable(LOCATION_KEY)
        )
    }
}

data class RestaurantItemViewModel(
    val restaurant: RestaurantCondensed,
    var isFavorite: Boolean
)