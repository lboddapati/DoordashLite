package com.interview.doordashlite.datalayer

import com.interview.doordashlite.models.RestaurantCondensed
import com.interview.doordashlite.models.RestaurantFull
import io.reactivex.Completable
import io.reactivex.Single

interface DataRepository {

    fun getRestaurantList(latitude: Float, longitude: Float): Single<List<RestaurantCondensed>>

    fun getRestaurant(id: String): Single<RestaurantFull>

    fun getFavoriteRestaurants(): Single<Set<String>>

    fun addFavoriteRestaurant(restaurantId: String): Completable

    fun removeFavoriteRestaurant(restaurantId: String): Completable
}