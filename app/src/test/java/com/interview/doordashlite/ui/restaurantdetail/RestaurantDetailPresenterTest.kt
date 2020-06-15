package com.interview.doordashlite.ui.restaurantdetail

import com.interview.doordashlite.base.testApplicationModule
import com.interview.doordashlite.datalayer.DataRepository
import com.interview.doordashlite.models.RestaurantFull
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

private const val RESTAURANT_ID = "restaurant_id"

class RestaurantDetailPresenterTest: KoinTest {
    @get:Rule
    val koinTestRule = KoinTestRule.create { modules(
        testApplicationModule,
        module {
            single<RestaurantDetailContract.View> { mock() }
        }
    )}

    private val dataRepository: DataRepository by inject()
    private val view: RestaurantDetailContract.View by inject()
    private val viewModel = RestaurantDetailViewModel(RESTAURANT_ID)

    @Test
    fun onCreate_loadsRestaurant() {
        whenever(dataRepository.getRestaurant(any()))
            .thenReturn(Single.just(mock()))
        RestaurantDetailPresenter(view, viewModel, get())
            .onCreate()

        verify(dataRepository).getRestaurant(RESTAURANT_ID)
    }

    @Test
    fun onRetryClicked_loadsRestaurant() {
        whenever(dataRepository.getRestaurant(any()))
            .thenReturn(Single.just(mock()))
        RestaurantDetailPresenter(view, viewModel, get())
            .onRetryClicked()

        verify(dataRepository).getRestaurant(RESTAURANT_ID)
    }

    @Test
    fun onRestaurantRequestSuccess_updatesViewWithResult() {
        val restaurant = mock<RestaurantFull>()
        whenever(dataRepository.getRestaurant(any()))
            .thenReturn(Single.just(restaurant))
        RestaurantDetailPresenter(view, viewModel, get())
            .onCreate()

        verify(view).displayRestaurant(restaurant)
    }

    @Test
    fun onRestaurantListRequestFailure_updatesViewWithError() {
        whenever(dataRepository.getRestaurant(any()))
            .thenReturn(Single.error(Throwable()))
        RestaurantDetailPresenter(view, viewModel, get())
            .onCreate()

        verify(view).displayError()
    }
}