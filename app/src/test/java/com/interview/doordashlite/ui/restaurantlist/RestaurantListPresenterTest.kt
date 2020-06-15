package com.interview.doordashlite.ui.restaurantlist

import com.interview.doordashlite.datalayer.DataRepository
import com.interview.doordashlite.models.RestaurantCondensed
import com.interview.doordashlite.base.testApplicationModule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
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

    @Test
    fun onCreate_loadsRestaurants() {
        whenever(dataRepository.getRestaurantList(any(), any()))
            .thenReturn(Single.just(listOf()))
        RestaurantListPresenter(view, RestaurantListViewModel(), router, get())
            .onCreate()

        verify(dataRepository).getRestaurantList(any(), any())
    }

    @Test
    fun onRetryClicked_loadsRestaurants() {
        whenever(dataRepository.getRestaurantList(any(), any()))
            .thenReturn(Single.just(listOf()))
        RestaurantListPresenter(view, RestaurantListViewModel(), router, get())
            .onRetryClicked()

        verify(dataRepository).getRestaurantList(any(), any())
    }

    @Test
    fun onRestaurantListRequestSuccess_emptyResults_updatesViewWithEmptyState() {
        whenever(dataRepository.getRestaurantList(any(), any()))
            .thenReturn(Single.just(listOf()))
        RestaurantListPresenter(view, RestaurantListViewModel(), router, get())
            .onCreate()

        verify(view).displayEmptyState()
    }

    @Test
    fun onRestaurantListRequestSuccess_nonEmptyResults_updatesViewWithResults() {
        val restaurants = listOf(RestaurantCondensed("", "", "", "", "", 0))
        whenever(dataRepository.getRestaurantList(any(), any()))
            .thenReturn(Single.just(restaurants))
        RestaurantListPresenter(view, RestaurantListViewModel(), router, get())
            .onCreate()

        verify(view).displayRestaurants(restaurants)
    }

    @Test
    fun onRestaurantListRequestFailure_updatesViewWithError() {
        whenever(dataRepository.getRestaurantList(any(), any()))
            .thenReturn(Single.error(Throwable()))
        RestaurantListPresenter(view, RestaurantListViewModel(), router, get())
            .onCreate()

        verify(view).displayError()
    }

    @Test
    fun onRestaurantSelected_delegatesToRouter() {
        val restaurant = RestaurantCondensed("someId", "", "", "", "", 0)
        RestaurantListPresenter(view, RestaurantListViewModel(), router, get())
            .onRestaurantSelected(restaurant)

        verify(router).openRestaurantDetail(restaurant.id)
    }
}