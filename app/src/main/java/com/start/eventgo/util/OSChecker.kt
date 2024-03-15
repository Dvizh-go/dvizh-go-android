package com.start.eventgo.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

const val REQUEST_PERMISSION_CODE = 2

object OSChecker {
    fun checkPermissionBasedOS(context: Context): Boolean {
        val readImagePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            Manifest.permission.READ_MEDIA_IMAGES
        else
            Manifest.permission.READ_EXTERNAL_STORAGE

        return ContextCompat.checkSelfPermission(context, readImagePermission) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermissionBasedOS(activity: Activity): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissions = arrayOf(
                Manifest.permission.READ_MEDIA_IMAGES,
            )

            if (!checkPermissionBasedOS(activity)) {
                ActivityCompat.requestPermissions(activity, permissions, REQUEST_PERMISSION_CODE)
                return true
            }
        } else {
            val permissions = arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            )

            if (!checkPermissionBasedOS(activity)) {
                ActivityCompat.requestPermissions(activity, permissions, REQUEST_PERMISSION_CODE)
                return true
            }
        }
        return false
    }
}
