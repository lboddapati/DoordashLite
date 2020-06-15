package com.interview.doordashlite.base

import androidx.lifecycle.Lifecycle
import com.interview.doordashlite.datalayer.DataRepository
import com.interview.doordashlite.datalayer.DataRepositoryImpl
import com.interview.doordashlite.ui.restaurantdetail.RestaurantDetailContract
import com.interview.doordashlite.ui.restaurantdetail.RestaurantDetailPresenter
import com.interview.doordashlite.ui.restaurantdetail.RestaurantDetailViewModel
import com.interview.doordashlite.ui.restaurantlist.RestaurantListContract
import com.interview.doordashlite.ui.restaurantlist.RestaurantListPresenter
import com.squareup.picasso.Picasso
import org.koin.dsl.module

val applicationModule = module {
    single<DataRepository> { DataRepositoryImpl() }
    single { SubscriptionConfig.createDefaultConfig() }
    single { Picasso.get() }
}

val presenterFactoryModule = module {
    factory<RestaurantListContract.Presenter> {
            (view: RestaurantListContract.View,
                router: RestaurantListContract.Router,
                lifecycle: Lifecycle) ->
        RestaurantListPresenter(view, router, LifecycleAwareSubscriptionManager(lifecycle))
    }
    factory<RestaurantDetailContract.Presenter> {
        (view: RestaurantDetailContract.View,
            viewModel: RestaurantDetailViewModel,
            lifecycle: Lifecycle) ->
        RestaurantDetailPresenter(view, viewModel, LifecycleAwareSubscriptionManager(lifecycle))
    }
}