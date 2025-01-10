package com.anondev.customplugin

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Log

class MockLocationDetector {
    fun getListOfFakeLocationAppsInstalled(context: Context): List<String> {
        val fakeApps: MutableList<String> = ArrayList()
        val pm = context.packageManager
        @SuppressLint("QueryPermissionsNeeded") val packages =
            pm.getInstalledApplications(PackageManager.GET_META_DATA)

        for (applicationInfo in packages) {
            try {
                val packageInfo = pm.getPackageInfo(
                    applicationInfo.packageName,
                    PackageManager.GET_PERMISSIONS
                )

                if (hasMockLocationPermission(packageInfo) && applicationInfo.packageName != context.packageName) {
                    fakeApps.add(getApplicationName(context, applicationInfo.packageName))
                }
            } catch (e: PackageManager.NameNotFoundException) {
                if (e.message != null && BuildConfig.DEBUG) Log.e("Got exception ", e.message!!)
            }
        }
        return fakeApps
    }

    private fun hasMockLocationPermission(packageInfo: PackageInfo): Boolean {
        if (packageInfo.requestedPermissions == null) return false
        val requestedPermissions = packageInfo.requestedPermissions
        for (requestedPermission in requestedPermissions) {
            if (requestedPermission == "android.permission.ACCESS_MOCK_LOCATION") {
                return true
            }
        }
        return false
    }

    private fun getApplicationName(context: Context, packageName: String): String {
        var appName = packageName
        val packageManager = context.packageManager
        try {
            appName = packageManager.getApplicationLabel(
                packageManager.getApplicationInfo(
                    packageName,
                    PackageManager.GET_META_DATA
                )
            ).toString()
        } catch (e: PackageManager.NameNotFoundException) {
            if (BuildConfig.DEBUG) {
                Log.e("Got exception ", e.toString())
            }
        }
        return appName
    }
}
