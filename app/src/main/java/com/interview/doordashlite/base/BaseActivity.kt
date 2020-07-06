package com.interview.doordashlite.base

import android.widget.Button
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.interview.doordashlite.R

abstract class BaseActivity: AppCompatActivity() {

    fun displayLoading() {
        setContentView(R.layout.loading_spinner)
    }

    fun displayError(
        @StringRes errorStringRes: Int = R.string.generic_error,
        retryAction: () -> Unit
    ) {
        setContentView(R.layout.error_panel)
        findViewById<TextView>(R.id.error_message).text = getString(errorStringRes)
        findViewById<Button>(R.id.retry_button).setOnClickListener { retryAction() }
    }
}