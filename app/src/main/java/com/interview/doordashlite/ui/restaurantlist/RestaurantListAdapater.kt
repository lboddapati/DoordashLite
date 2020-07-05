package com.interview.doordashlite.ui.restaurantlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.interview.doordashlite.R
import com.squareup.picasso.Picasso
import org.koin.core.KoinComponent
import org.koin.core.inject

class RestaurantListAdapter(
    private val presenter: RestaurantListContract.Presenter
): RecyclerView.Adapter<RestaurantListItemViewHolder>() {
    private val restaurants = arrayListOf<RestaurantItemViewModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = RestaurantListItemViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.restaurant_list_item, parent, false),
        presenter
    )

    override fun onBindViewHolder(holder: RestaurantListItemViewHolder, position: Int) {
        holder.bind(restaurants[position])
    }

    override fun getItemCount() = restaurants.size

    fun addRestaurants(newRestaurants: List<RestaurantItemViewModel>) {
        val positionStart = this.restaurants.size
        restaurants.addAll(newRestaurants)
        notifyItemRangeChanged(positionStart, newRestaurants.size)
    }
}

class RestaurantListItemViewHolder(
    view: View,
    presenter: RestaurantListContract.Presenter
): RecyclerView.ViewHolder(view), KoinComponent {
    private val picasso: Picasso by inject()

    private val thumbnail = view.findViewById<ImageView>(R.id.thumbnail)
    private val name = view.findViewById<TextView>(R.id.name)
    private val description = view.findViewById<TextView>(R.id.description)
    private val status = view.findViewById<TextView>(R.id.status)
    private val favorite = view.findViewById<ImageView>(R.id.favorite)

    private lateinit var selectedRestaurant: RestaurantItemViewModel

    init {
        view.setOnClickListener { presenter.onRestaurantSelected(selectedRestaurant.restaurant) }
        favorite.setOnClickListener {
            presenter.onFavoriteClicked(selectedRestaurant)
            selectedRestaurant.isFavorite = !selectedRestaurant.isFavorite
            updateFavoriteState(selectedRestaurant)
        }
    }

    fun bind(item: RestaurantItemViewModel) {
        picasso.load(item.restaurant.thumbnailUrl).into(thumbnail)
        name.text = item.restaurant.name
        description.text = item.restaurant.description
        status.text = item.restaurant.status
        selectedRestaurant = item
        updateFavoriteState(item)
    }

    private fun updateFavoriteState(item: RestaurantItemViewModel) {
        val drawableResId = if (item.isFavorite) {
            android.R.drawable.star_on
        } else {
            android.R.drawable.star_off
        }
        favorite.setImageResource(drawableResId)
    }
}