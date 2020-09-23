package com.interview.doordashlite.datalayer

import com.interview.doordashlite.models.LoginRequestBody
import com.interview.doordashlite.models.LoginResponse
import com.interview.doordashlite.models.RestaurantCondensed
import com.interview.doordashlite.models.RestaurantFull
import io.reactivex.Single
import retrofit2.http.*

interface DoordashApi {

    @GET("restaurant/")
    fun getRestaurantList(
        @Query("lat") latitude: Float,
        @Query("lng") longitude: Float,
        @Query("limit") limit: Int = 20
    ) : Single<List<RestaurantCondensed>>

    @GET("restaurant/{id}/")
    fun getRestaurant(
        @Path("id") id: String
    ) : Single<RestaurantFull>

    @POST("auth/token/")
    fun login(
        @Body requestBody: LoginRequestBody
    ): Single<LoginResponse>
}