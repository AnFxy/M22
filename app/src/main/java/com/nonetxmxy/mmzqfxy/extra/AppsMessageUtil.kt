package com.nonetxmxy.mmzqfxy.extra

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

object AppsMessageUtil {

    fun scanLocalInstallAppList(packageManager: PackageManager): List<DownloadAppBean> {
        val myAppInfos: MutableList<DownloadAppBean> = ArrayList()
        try {
            val packageInfos =
                packageManager.getInstalledPackages(0)
            for (i in packageInfos.indices) {
                val packageInfo = packageInfos[i]
                val myAppInfo = DownloadAppBean()
                myAppInfo.GebHu = 0
                //过滤掉系统app
                if (ApplicationInfo.FLAG_SYSTEM and packageInfo.applicationInfo.flags != 0) {
                    myAppInfo.GebHu = 1
                }
                val appName = packageInfo.applicationInfo.loadLabel(packageManager).toString()
                myAppInfo.QPUVJnypX = appName
                myAppInfo.jZPUSQDYSqa = packageInfo.versionName ?: ""
                myAppInfo.XYIsTHUYn = "" // TODO googleaid id
                myAppInfo.BjHXX = packageInfo.packageName
                myAppInfo.FVa = packageInfo.firstInstallTime
                myAppInfo.RBNeecgaucU = packageInfo.lastUpdateTime
                if (ApplicationInfo.FLAG_SYSTEM and packageInfo.applicationInfo.flags != 0) {
                    myAppInfo.GebHu = 1
                } else {
                    myAppInfo.GebHu = 0
                }
                myAppInfos.add(myAppInfo)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return myAppInfos
    }
}

class DownloadAppBean(
    var QPUVJnypX: String = "",
    var jZPUSQDYSqa: String = "",
    var XYIsTHUYn: String = "",
    var BjHXX: String = "",
    var FVa: Long = 0L,
    var RBNeecgaucU: Long = 0L,
    var GebHu: Int = 0,
)