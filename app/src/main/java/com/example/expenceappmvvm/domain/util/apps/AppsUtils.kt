package com.example.expenceappmvvm.domain.util.apps

import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager.GET_META_DATA


fun Context.getInstalledAppInfo(appPackageName: String): PackageInfo? =
    packageManager.getInstalledPackages(GET_META_DATA).firstOrNull {
        it?.packageName == appPackageName
    }

fun Context.getInstalledAppVersion(appPackageName: String): Int? =
    getInstalledAppInfo(appPackageName)?.versionCode

fun Context.isAppRunning(appPackageName: String): Boolean {
    val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
    return activityManager?.runningAppProcesses?.any { it.processName == appPackageName } == true
}

fun Context.isServiceRunning(appPackageName: String): Boolean {
    val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
    return activityManager?.getRunningServices(Int.MAX_VALUE)
        ?.firstOrNull { it.process == appPackageName }?.started == true
}