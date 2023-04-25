package com.dev_musashi.weather.data.location

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import com.dev_musashi.weather.domain.location.LocationTracker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.resume

@Suppress("DEPRECATION")
class LocationTrackerImpl @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    private val application: Application
) : LocationTracker {
    private lateinit var currentLocation : LocationResult

    private val geo = Geocoder(application.applicationContext, Locale.KOREA)

    private val locationRequest = LocationRequest.create().apply {
        priority = Priority.PRIORITY_HIGH_ACCURACY
        interval = 20 * 1000
    }

    private val requestCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            Log.d("TAG", "onLocationResult: ${p0.locations}")
            currentLocation = p0
            locationClient.removeLocationUpdates(this)
        }
    }

    private val locationManager = application.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    override suspend fun getCurrentLocation(): Pair<Location?, Address?>? {

        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            application, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val checkGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val checkNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        val checkPermission = !(!hasAccessCoarseLocationPermission || !hasAccessFineLocationPermission || !checkGPS || !checkNetwork)

        return if (checkPermission) {
            suspendCancellableCoroutine { cont ->
                locationClient.lastLocation
                    .addOnCompleteListener {
                        if (it.result != null) {
                            val address = geo.getFromLocation(it.result.latitude, it.result.longitude, 1)
                            cont.resume(Pair(it.result, address!![0]))
                        } else {
                            locationUpdate()
                            val location = currentLocation.locations.first()
                            val address = geo.getFromLocation(location.latitude, location.longitude, 1)
                            cont.resume(Pair(location, address!![0]))
                        }
                    }
            }
        } else {
            Pair(null, null)
        }
    }

    private fun locationUpdate() {
        try {
            locationClient.requestLocationUpdates(
                locationRequest,
                requestCallback,
                Looper.getMainLooper()
            )
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

}