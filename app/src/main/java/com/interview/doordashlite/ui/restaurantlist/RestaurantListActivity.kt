package com.interview.doordashlite.ui.restaurantlist

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.interview.doordashlite.R
import com.interview.doordashlite.base.BaseActivity
import com.interview.doordashlite.models.ErrorType
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf

private const val REQUEST_LOCATION_PERMISSION = 999
private const val REQUEST_CHECK_LOCATION_SETTINGS = 777
private const val LOCATION_REQUEST_INTERVAL = 10000L
private const val LOCATION_REQUEST_FASTEST_INTERVAL = 5000L
private const val SCROLL_END_OFFSET = 5

/**
 * Activity for displaying a list of restaurants around a given location
 */
class RestaurantListActivity: BaseActivity(), RestaurantListContract.View {

    private lateinit var presenter: RestaurantListContract.Presenter
    private val adapter: RestaurantListAdapter by lazy { RestaurantListAdapter(presenter) }
    private val fusedLocationClient by lazy { LocationServices.getFusedLocationProviderClient(this) }
    private val locationRequest by lazy { LocationRequest().apply {
        interval = LOCATION_REQUEST_INTERVAL
        fastestInterval = LOCATION_REQUEST_FASTEST_INTERVAL
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY;
    } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.discover)
        presenter = get {
            val viewModel = if (savedInstanceState != null) {
                RestaurantListViewModel.fromBundle(savedInstanceState)
            } else {
                RestaurantListViewModel()
            }
            parametersOf(this, viewModel, RestaurantListRouter(this), lifecycle)
        }
    }

    override fun displayRestaurants(
        restaurants: List<RestaurantItemViewModel>,
        isFirstLoad: Boolean
    ) {
        if (isFirstLoad) {
            setContentView(R.layout.recyclerview)
            setupRecyclerView()
        }
        adapter.addRestaurants(restaurants)
    }

    override fun displayEmptyState() {
        setContentView(R.layout.restaurant_list_empty_state)
    }

    override fun displayError(errorType: ErrorType) = displayError (errorType.errorResId) {
        presenter.onRetryClicked(errorType)
    }

    override fun checkAndGetLocation() {
        val locationPermission = ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
        if (locationPermission != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        } else {
            getLocation()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION ->
                if (grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED) {
                    getLocation()
                } else {
                    displayError(ErrorType.LOCATION_PERMISSION_DENIED)
                }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.onSaveInstanceState(outState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CHECK_LOCATION_SETTINGS -> {
                if (resultCode == Activity.RESULT_OK) {
                    requestLocation()
                } else {
                    displayError(ErrorType.NO_LOCATION)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview).apply {
            layoutManager = LinearLayoutManager(this@RestaurantListActivity)
            adapter = this@RestaurantListActivity.adapter
            addItemDecoration(
                DividerItemDecoration(
                    this@RestaurantListActivity,
                    RecyclerView.VERTICAL
                )
            )

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    (recyclerView.layoutManager as? LinearLayoutManager)?.let {
                        val lastVisibleItemPosition = it.findLastCompletelyVisibleItemPosition()
                        val lastAdapterItemPosition =
                            this@RestaurantListActivity.adapter.itemCount - 1
                        if (lastVisibleItemPosition > 0 &&
                            lastVisibleItemPosition == lastAdapterItemPosition - SCROLL_END_OFFSET
                        ) {
                            presenter.onScrolledToEnd()
                        }
                    }
                }
            })
        }
    }

    private fun getLocation() {
        val locationSettingsRequest = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .build()
        LocationServices.getSettingsClient(this)
            .checkLocationSettings(locationSettingsRequest)
            .apply {
                addOnSuccessListener { requestLocation() }
                addOnFailureListener { error ->
                    if (error is ResolvableApiException){
                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            error.startResolutionForResult(
                                this@RestaurantListActivity,
                                REQUEST_CHECK_LOCATION_SETTINGS
                            )
                        } catch (exception: IntentSender.SendIntentException) {
                            displayError(ErrorType.NO_LOCATION)
                        }
                    }
                }
            }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocation() {
        val locationCallback = object: LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                presenter.onLocationReceived(result.lastLocation)
                fusedLocationClient.removeLocationUpdates(this)
            }
        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }
}