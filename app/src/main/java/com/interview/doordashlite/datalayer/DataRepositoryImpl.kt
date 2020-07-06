package com.interview.doordashlite.datalayer

import android.content.SharedPreferences
import io.reactivex.Completable
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://api.doordash.com/v2/"
private const val FAVORITE_RESTAURANTS_KEY = "favorite_restaurants"

// TODO: Implement caching for the responses
class DataRepositoryImpl: DataRepository, KoinComponent {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    private val doordashApi = retrofit.create(DoordashApi::class.java)

    private val sharedPreferences by inject<SharedPreferences>()

    override fun getRestaurantList(
        latitude: Double,
        longitude: Double
    ) = doordashApi.getRestaurantList(latitude, longitude)

    override fun getRestaurant(id: String) = doordashApi.getRestaurant(id)

    override fun getFavoriteRestaurants() = Single.just(
        sharedPreferences.getStringSet(FAVORITE_RESTAURANTS_KEY, setOf()) ?: setOf()
    )

    override fun addFavoriteRestaurant(restaurantId: String) = Completable.create { emitter ->
        val savedFavRestaurantIds = sharedPreferences.getStringSet(FAVORITE_RESTAURANTS_KEY, setOf()) ?: setOf()
        val updatedFavRestaurantIds = savedFavRestaurantIds.toMutableSet().apply {
            add(restaurantId)
        }
        if (sharedPreferences.edit()
            .putStringSet(FAVORITE_RESTAURANTS_KEY, updatedFavRestaurantIds)
            .commit()) {
            emitter.onComplete()
        } else {
            emitter.onError(Error("Error adding $restaurantId to favorites."))
        }
    }

    override fun removeFavoriteRestaurant(restaurantId: String) = Completable.create { emitter ->
        val savedFavRestaurantIds = sharedPreferences.getStringSet(FAVORITE_RESTAURANTS_KEY, setOf()) ?: setOf()
        val updatedFavRestaurantIds = savedFavRestaurantIds.toMutableSet().apply {
            remove(restaurantId)
        }
        if (sharedPreferences.edit()
                .putStringSet(FAVORITE_RESTAURANTS_KEY, updatedFavRestaurantIds)
                .commit()) {
            emitter.onComplete()
        } else {
            emitter.onError(Error("Error removing $restaurantId from favorites."))
        }
    }
}