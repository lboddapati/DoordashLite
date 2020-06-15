package com.interview.doordashlite.base

import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.interview.doordashlite.R

abstract class BaseActivity: AppCompatActivity() {

    fun displayLoading() {
        setContentView(R.layout.loading_spinner)
    }

    fun displayError(retryAction: () -> Unit) {
        setContentView(R.layout.error_panel)
        findViewById<Button>(R.id.retry_button).setOnClickListener { retryAction() }
    }
}