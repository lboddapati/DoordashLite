package com.interview.doordashlite.ui.restaurantlist

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.interview.doordashlite.R
import com.interview.doordashlite.base.BaseActivity
import com.interview.doordashlite.models.RestaurantCondensed
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf

/**
 * Activity for displaying a list of restaurants around a given location
 */
class RestaurantListActivity: BaseActivity(), RestaurantListContract.View {

    private val presenter: RestaurantListContract.Presenter = get {
        parametersOf(this, RestaurantListRouter(this), lifecycle)
    }
    private val adapter: RestaurantListAdapter by lazy { RestaurantListAdapter(presenter) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.discover)
    }

    override fun displayRestaurants(restaurants: List<RestaurantCondensed>) {
        setContentView(R.layout.recyclerview)
        setupRecyclerView()
        adapter.addRestaurants(restaurants)
    }

    override fun displayEmptyState() {
        setContentView(R.layout.restaurant_list_empty_state)
    }

    override fun displayError() = displayError { presenter.onRetryClicked() }

    private fun setupRecyclerView() {
        findViewById<RecyclerView>(R.id.recyclerview).apply {
            layoutManager = LinearLayoutManager(this@RestaurantListActivity)
            adapter = this@RestaurantListActivity.adapter
            addItemDecoration(DividerItemDecoration(
                this@RestaurantListActivity,
                RecyclerView.VERTICAL
            ))
        }
    }
}