package com.interview.doordashlite.ui.restaurantlist

import android.widget.FrameLayout
import androidx.test.core.app.ApplicationProvider
import com.interview.doordashlite.models.RestaurantCondensed
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class RestaurantListAdapterTest {

    @Test
    fun getItemCount_returnsCorrectValue() {
        val adapter = RestaurantListAdapter(mock())
        assertEquals(0, adapter.itemCount)

        adapter.addRestaurants(listOf(mock()))
        assertEquals(1, adapter.itemCount)

        adapter.addRestaurants(listOf(mock(), mock()))
        assertEquals(3, adapter.itemCount)
    }

    @Test
    fun onBindViewHolder_delegatesToViewHolder() {
        val viewHolder = mock<RestaurantListItemViewHolder>()
        val restaurant = mock<RestaurantItemViewModel>()
        RestaurantListAdapter(mock()).apply {
            addRestaurants(listOf(restaurant))
            onBindViewHolder(viewHolder, 0)
        }

        verify(viewHolder).bind(restaurant)
    }

    @Test
    fun onItemClicked_delegatesToPresenter() {
        val presenter = mock<RestaurantListContract.Presenter>()
        val restaurant = RestaurantCondensed("", "", "", "", "", 0)
        RestaurantListAdapter(presenter).apply {
            addRestaurants(listOf(RestaurantItemViewModel(restaurant, false)))
            // Setup and bind viewholder
            val viewHolder = onCreateViewHolder(
                FrameLayout(ApplicationProvider.getApplicationContext()), 0)
            onBindViewHolder(viewHolder, 0)
            // Simulate item click
            viewHolder.itemView.callOnClick()
        }

        verify(presenter).onRestaurantSelected(restaurant)
    }
}