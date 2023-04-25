package com.dev_musashi.weather.domain.location

import android.location.Address
import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Pair<Location?, Address?>?
}