package com.interview.doordashlite.models

import androidx.annotation.StringRes
import com.interview.doordashlite.R

enum class ErrorType(@StringRes val errorResId: Int) {
    GENERIC(R.string.generic_error),
    NO_LOCATION(R.string.no_location_error),
    LOCATION_PERMISSION_DENIED(R.string.location_permission_denied_error)
}