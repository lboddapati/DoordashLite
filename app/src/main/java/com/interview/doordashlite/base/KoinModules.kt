package com.interview.doordashlite.base

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.Lifecycle
import com.interview.doordashlite.datalayer.DataRepository
import com.interview.doordashlite.datalayer.DataRepositoryImpl
import com.interview.doordashlite.ui.restaurantdetail.RestaurantDetailContract
import com.interview.doordashlite.ui.restaurantdetail.RestaurantDetailPresenter
import com.interview.doordashlite.ui.restaurantdetail.RestaurantDetailViewModel
import com.interview.doordashlite.ui.restaurantlist.RestaurantListContract
import com.interview.doordashlite.ui.restaurantlist.RestaurantListPresenter
import com.interview.doordashlite.ui.restaurantlist.RestaurantListViewModel
import com.squareup.picasso.Picasso
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val SHARED_PREFS_NAME = "DoordashSharedPrefs"

val applicationModule = module {
    single<DataRepository> { DataRepositoryImpl() }
    single { androidContext().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE) }
    single { SubscriptionConfig.createDefaultConfig() }
    single { Picasso.get() }
}

val presenterFactoryModule = module {
    factory<RestaurantListContract.Presenter> {
            (view: RestaurantListContract.View,
                router: RestaurantListContract.Router,
                lifecycle: Lifecycle) ->
        RestaurantListPresenter(
            view,
            RestaurantListViewModel(),
            router,
            LifecycleAwareSubscriptionManager(lifecycle)
        ).apply { lifecycle.addObserver(this) }
    }

    factory<RestaurantDetailContract.Presenter> {
        (view: RestaurantDetailContract.View,
            viewModel: RestaurantDetailViewModel,
            lifecycle: Lifecycle) ->
        RestaurantDetailPresenter(
            view,
            viewModel,
            LifecycleAwareSubscriptionManager(lifecycle)
        ).apply { lifecycle.addObserver(this) }
    }
}