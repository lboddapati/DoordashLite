package com.interview.doordashlite.ui.restaurantlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.interview.doordashlite.R
import com.interview.doordashlite.models.RestaurantCondensed
import com.squareup.picasso.Picasso

class RestaurantListAdapter(
    private val presenter: RestaurantListContract.Presenter
): RecyclerView.Adapter<RestaurantListItemViewHolder>() {
    private val restaurants = arrayListOf<RestaurantCondensed>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = RestaurantListItemViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.restaurant_list_item, parent, false),
        presenter
    )

    override fun onBindViewHolder(holder: RestaurantListItemViewHolder, position: Int) {
        holder.bindViewHolder(restaurants[position])
    }

    override fun getItemCount() = restaurants.size

    fun addRestaurants(newRestaurants: List<RestaurantCondensed>) {
        val positionStart = this.restaurants.size
        restaurants.addAll(newRestaurants)
        notifyItemRangeChanged(positionStart, newRestaurants.size)
    }
}

class RestaurantListItemViewHolder(
    view: View,
    presenter: RestaurantListContract.Presenter
): RecyclerView.ViewHolder(view) {
    private val thumbnail = view.findViewById<ImageView>(R.id.thumbnail)
    private val name = view.findViewById<TextView>(R.id.name)
    private val description = view.findViewById<TextView>(R.id.description)
    private val status = view.findViewById<TextView>(R.id.status)

    private lateinit var selectedRestaurant: RestaurantCondensed

    init {
        view.setOnClickListener { presenter.onRestaurantSelected(selectedRestaurant) }
    }

    fun bindViewHolder(restaurant: RestaurantCondensed) {
        Picasso.get().load(restaurant.thumbnailUrl).into(thumbnail)
        name.text = restaurant.name
        description.text = restaurant.description
        status.text = restaurant.status
        selectedRestaurant = restaurant
    }
}