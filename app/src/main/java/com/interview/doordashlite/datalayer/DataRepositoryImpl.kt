package com.interview.doordashlite.datalayer

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://api.doordash.com/v2/"

// TODO: Implement caching for the responses
class DataRepositoryImpl: DataRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    private val doordashApi = retrofit.create(DoodashApi::class.java)

    override fun getRestaurantList(
        latitude: Float,
        longitude: Float
    ) = doordashApi.getRestaurantList(latitude, longitude)

    override fun getRestaurant(id: String) = doordashApi.getRestaurant(id)
}