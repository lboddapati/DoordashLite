package com.interview.doordashlite.models

import com.squareup.moshi.Json

data class MenuCondensed(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "subtitle") val subtitle: String,
    @field:Json(name = "status") val status: String
)