package com.interview.doordashlite.datalayer

import com.interview.doordashlite.models.RestaurantCondensed
import com.interview.doordashlite.models.RestaurantFull
import io.reactivex.Single

interface DataRepository {

    fun getRestaurantList(latitude: Float, longitude: Float): Single<List<RestaurantCondensed>>

    fun getRestaurant(id: String): Single<RestaurantFull>
}