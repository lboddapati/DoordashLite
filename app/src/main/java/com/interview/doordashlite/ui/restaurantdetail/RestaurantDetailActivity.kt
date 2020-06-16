package com.interview.doordashlite.ui.restaurantdetail

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.interview.doordashlite.R
import com.interview.doordashlite.base.BaseActivity
import com.interview.doordashlite.models.RestaurantFull
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

/**
 * Activity for displaying a detailed view of a single Restaurant
 */
class RestaurantDetailActivity: BaseActivity(), RestaurantDetailContract.View {

    private lateinit var presenter: RestaurantDetailContract.Presenter
    private val picasso: Picasso by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = get { parametersOf(
            this,
            RestaurantDetailRouter.createViewModel(intent),
            lifecycle
        )}
    }

    override fun displayRestaurant(restaurant: RestaurantFull) {
        setContentView(R.layout.restaurant_detail_activity)
        configureHeader(restaurant)
        configureContent(restaurant)
    }

    override fun displayError() = displayError { presenter.onRetryClicked() }

    private fun configureHeader(restaurant: RestaurantFull) {
        val appbar = findViewById<AppBarLayout>(R.id.appbar)
        appbar.findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar).title = restaurant.name

        findViewById<ImageView>(R.id.cover_image).apply {
            if (restaurant.coverImageUrl.isNullOrBlank()) {
                visibility = View.GONE
                appbar.setExpanded(false)
            } else {
                picasso.load(restaurant.coverImageUrl).into(this)
            }
        }
    }

    private fun configureContent(restaurant: RestaurantFull) {
        findViewById<TextView>(R.id.description).text = restaurant.description
        findViewById<TextView>(R.id.details).text = RestaurantUtils.getDetailsString(
            resources,
            restaurant
        )
        findViewById<RecyclerView>(R.id.menu_list).apply {
            layoutManager = LinearLayoutManager(this@RestaurantDetailActivity)
            adapter = MenuListAdapter(restaurant.menus, presenter)
            addItemDecoration(DividerItemDecoration(this@RestaurantDetailActivity, RecyclerView.VERTICAL))
        }
    }
}