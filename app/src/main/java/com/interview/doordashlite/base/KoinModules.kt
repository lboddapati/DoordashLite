package com.interview.doordashlite.base

import com.interview.doordashlite.datalayer.DataRepository
import com.interview.doordashlite.datalayer.DataRepositoryImpl
import com.interview.doordashlite.ui.restaurantdetail.RestaurantDetailContract
import com.interview.doordashlite.ui.restaurantdetail.RestaurantDetailPresenter
import com.interview.doordashlite.ui.restaurantdetail.RestaurantDetailViewModel
import com.interview.doordashlite.ui.restaurantlist.RestaurantListContract
import com.interview.doordashlite.ui.restaurantlist.RestaurantListPresenter
import org.koin.dsl.module

val dataRepositoryModule = module {
    single<DataRepository> { DataRepositoryImpl() }
}

val presenterFactoryModule = module {
    factory<RestaurantListContract.Presenter> {
            (view: RestaurantListContract.View, router: RestaurantListContract.Router) ->
        RestaurantListPresenter(view, router)
    }
    factory<RestaurantDetailContract.Presenter> {
        (view: RestaurantDetailContract.View, viewModel: RestaurantDetailViewModel) ->
        RestaurantDetailPresenter(view, viewModel)
    }
}