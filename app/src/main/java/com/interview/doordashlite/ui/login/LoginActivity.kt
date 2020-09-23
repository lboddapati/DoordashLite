package com.interview.doordashlite.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.interview.doordashlite.R
import com.interview.doordashlite.base.BaseActivity
import com.interview.doordashlite.base.LifecycleAwareSubscriptionManager
import com.interview.doordashlite.ui.restaurantlist.RestaurantListActivity

class LoginActivity: BaseActivity(), LoginContract.Router {

    private val presenter = LoginPresenter(this, LifecycleAwareSubscriptionManager(lifecycle))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        setupView()
    }

    private fun setupView() {
        findViewById<Button>(R.id.login).setOnClickListener {
            // TODO: empty field checks
            val email = findViewById<EditText>(R.id.email).text.toString()
            val password = findViewById<EditText>(R.id.password).text.toString()
            presenter.onLoginClicked(email, password)
        }
    }

    override fun startRestaurantListActivity() {
        startActivity(
            Intent(this, RestaurantListActivity::class.java)
        )
    }
}