package com.interview.doordashlite.ui.restaurantlist

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.interview.doordashlite.R
import com.interview.doordashlite.models.RestaurantCondensed
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class RestaurantListActivity: AppCompatActivity(), RestaurantListContract.View {

    private val presenter: RestaurantListContract.Presenter by inject {
        parametersOf(this, RestaurantListRouter(this))
    }
    private lateinit var adapter: RestaurantListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.discover)
        setContentView(R.layout.recyclerview)
        setupRecyclerView()
        presenter.onCreate()
    }

    override fun displayRestaurants(restaurants: List<RestaurantCondensed>) {
        adapter.addRestaurants(restaurants)
    }

    override fun displayError(error: Throwable) {
        // TODO: Display more useful error message and hook up retry logic
        Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
            .show()
    }

    private fun setupRecyclerView() {
        adapter = RestaurantListAdapter(presenter)
        findViewById<RecyclerView>(R.id.recyclerview).apply {
            layoutManager = LinearLayoutManager(this@RestaurantListActivity)
            adapter = this@RestaurantListActivity.adapter
        }
    }
}