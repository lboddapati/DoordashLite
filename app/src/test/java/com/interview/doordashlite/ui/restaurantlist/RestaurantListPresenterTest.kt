package com.interview.doordashlite.ui.restaurantlist

import android.location.Location
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import com.interview.doordashlite.base.testApplicationModule
import com.interview.doordashlite.datalayer.DataRepository
import com.interview.doordashlite.models.ErrorType
import com.interview.doordashlite.models.RestaurantCondensed
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get
import org.koin.test.inject

class RestaurantListPresenterTest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create { modules(
        testApplicationModule,
        module {
            single<RestaurantListContract.View> { mock() }
            single<RestaurantListContract.Router> { mock() }
        }
    )}

    private val dataRepository: DataRepository by inject()
    private val view: RestaurantListContract.View by inject()
    private val router: RestaurantListContract.Router by inject()

    private val viewModel = RestaurantListViewModel(Location(""))

    @Before
    fun setUp() {
        whenever(dataRepository.getFavoriteRestaurants()).thenReturn(Single.just(setOf()))
    }

    @Test
    fun onCreate_withoutLocation_getsLocation() {
        LifecycleRegistry(mock()).apply {
            addObserver(RestaurantListPresenter(view, RestaurantListViewModel(), router, get()))
            handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        }

        verify(view).checkAndGetLocation()
    }

    @Test
    fun onRetryClicked_withGenericErrorType_loadsRestaurants() {
        whenever(dataRepository.getRestaurantList(any(), any()))
            .thenReturn(Single.just(listOf()))
        RestaurantListPresenter(view, viewModel, router, get())
            .onRetryClicked(ErrorType.GENERIC)

        verify(dataRepository).getRestaurantList(any(), any())
    }

    @Test
    fun onRestaurantListRequestSuccess_emptyResults_updatesViewWithEmptyState() {
        whenever(dataRepository.getRestaurantList(any(), any()))
            .thenReturn(Single.just(listOf()))
        // Trigger request
        RestaurantListPresenter(view, viewModel, router, get())
            .onRetryClicked(ErrorType.GENERIC)

        verify(view).displayEmptyState()
    }

    @Test
    fun onRestaurantListRequestSuccess_nonEmptyResults_updatesViewWithResults() {
        whenever(dataRepository.getRestaurantList(any(), any()))
            .thenReturn(Single.just(listOf(mock<RestaurantCondensed>())))
        // Trigger request
        RestaurantListPresenter(view, viewModel, router, get())
            .onRetryClicked(ErrorType.GENERIC)

        verify(view).displayRestaurants(any())
    }

    @Test
    fun onRestaurantListRequestFailure_updatesViewWithError() {
        whenever(dataRepository.getRestaurantList(any(), any()))
            .thenReturn(Single.error(Throwable()))
        // Trigger request
        RestaurantListPresenter(view, viewModel, router, get())
            .onRetryClicked(ErrorType.GENERIC)

        verify(view).displayError(ErrorType.GENERIC)
    }

    @Test
    fun onRestaurantSelected_delegatesToRouter() {
        val restaurant = RestaurantCondensed("someId", "", "", "", "", 0)
        RestaurantListPresenter(view, viewModel, router, get())
            .onRestaurantSelected(restaurant)

        verify(router).openRestaurantDetail(restaurant.id)
    }
}