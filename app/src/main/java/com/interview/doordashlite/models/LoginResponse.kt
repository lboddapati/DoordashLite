package com.interview.doordashlite.models

import com.squareup.moshi.Json

data class LoginResponse(
    @field:Json(name = "token") val token: String
)