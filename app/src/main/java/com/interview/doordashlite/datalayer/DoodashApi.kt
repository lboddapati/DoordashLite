package com.interview.doordashlite.datalayer

import com.interview.doordashlite.models.RestaurantCondensed
import com.interview.doordashlite.models.RestaurantFull
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DoodashApi {

    @GET("restaurant/")
    fun getRestaurantList(
        @Query("lat") latitude: Float,
        @Query("lng") longitude: Float
    ) : Single<List<RestaurantCondensed>>

    @GET("restaurant/{id}/")
    fun getRestaurant(
        @Path("id") id: String
    ) : Single<RestaurantFull>
}