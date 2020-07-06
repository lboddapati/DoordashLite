package com.interview.doordashlite.datalayer

import com.interview.doordashlite.models.RestaurantCondensed
import com.interview.doordashlite.models.RestaurantFull
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DoordashApi {

    @GET("restaurant/")
    fun getRestaurantList(
        @Query("lat") latitude: Double,
        @Query("lng") longitude: Double,
        @Query("limit") limit: Int = 20
    ) : Single<List<RestaurantCondensed>>

    @GET("restaurant/{id}/")
    fun getRestaurant(
        @Path("id") id: String
    ) : Single<RestaurantFull>
}