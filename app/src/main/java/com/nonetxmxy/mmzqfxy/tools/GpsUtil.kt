package com.nonetxmxy.mmzqfxy.tools

import android.annotation.SuppressLint
import android.content.Context
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import timber.log.Timber
import java.lang.ref.WeakReference

class GpsUtil(paramContext: Context) {
    var location: Location? = null
        private set
    private var locationManager: LocationManager? = null

    //位置信息更新监听
    private val mLocationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(param1Location: Location) {
            location = param1Location
        }

        override fun onProviderDisabled(param1String: String) {
            Timber.d("onProviderDisabled$param1String")
        }

        override fun onProviderEnabled(param1String: String) {
            Timber.d("onProviderEnabled$param1String")
        }

        override fun onStatusChanged(param1String: String, param1Int: Int, param1Bundle: Bundle) {
            Timber.d("onStatusChanged$param1String")
        }
    }
    private val mContext: WeakReference<Context>
    private var locationType: String? = null

    init {
        mContext = WeakReference(paramContext)
        gPSLocation
    }

    //根据网络获取信息
    @get:SuppressLint("MissingPermission")
    private val netLocation: Location?
        private get() {
            var location1: Location? = null
            try {
                if (locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    locationType = LocationManager.NETWORK_PROVIDER
                    locationManager!!.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        0L,
                        0.0f,
                        mLocationListener
                    )
                    location1 = locationManager!!.getLastKnownLocation(locationType!!)
                }
            } catch (exception: Exception) {
                Timber.e(if (exception.message != null) exception.message else exception.toString())
            }
            return location1
        }

    @get:SuppressLint("MissingPermission")
    val gPSLocation: Unit
        get() {
            locationManager =
                mContext.get()!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            var location1: Location? = null
            try {
                if (!hasLocation()) return
                if (locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    val criteria = Criteria()
                    criteria.accuracy = Criteria.ACCURACY_FINE
                    criteria.isAltitudeRequired = false
                    criteria.isBearingRequired = false
                    criteria.isCostAllowed = true
                    criteria.powerRequirement = Criteria.POWER_MEDIUM
                    locationType = locationManager!!.getBestProvider(criteria, true)
                    locationManager!!.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        30L,
                        1f,
                        mLocationListener
                    )
                    location1 = locationManager!!.getLastKnownLocation(locationType!!)
                    if (location1 == null) {
                        location1 = netLocation
                    }
                } else {
                    location1 = netLocation
                }
            } catch (exception: Exception) {
                if (exception.message == null) {
                    Timber.e(exception.toString())
                } else {
                    Timber.e(exception.message)
                }
            }
            location = location1
        }

    fun hasLocation(): Boolean {
        return if (locationManager == null) false else locationManager!!.isProviderEnabled(
            LocationManager.GPS_PROVIDER
        ) || locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    fun hasLocationOfGps(): Boolean {
        var bool = false
        if (locationManager == null) return false
        if (locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) bool = true
        return bool
    }
}