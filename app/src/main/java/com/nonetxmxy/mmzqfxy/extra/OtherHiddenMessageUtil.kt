package com.nonetxmxy.mmzqfxy.extra

import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.SystemClock
import com.blankj.utilcode.util.Utils

object OtherHiddenMessageUtil {

    fun getBootDate() = System.currentTimeMillis() - SystemClock.elapsedRealtime()

    fun getOpenTime() = SystemClock.elapsedRealtime() / 1000

    fun getDianLiang() =
        Utils.getApp().registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            ?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)

    fun getMaxDianLiang() =
        Utils.getApp().registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            ?.getIntExtra(BatteryManager.EXTRA_SCALE, 100)
}