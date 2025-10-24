
package com.llucs.activitysend.core

import android.content.Context
import android.widget.Toast
import dev.rikka.shizuku.Shizuku
import dev.rikka.shizuku.Shizuku.OnRequestPermissionResultListener
import android.content.pm.PackageManager
import com.llucs.activitysend.R

object ShizukuHelper {

    private const val SHIZUKU_PERMISSION_REQUEST_CODE = 100

    fun hasShizukuPermission(): Boolean {
        return if (Shizuku.pingBinder()) {
            Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED
        } else {
            false
        }
    }

    fun requestShizukuPermission(context: Context, listener: OnRequestPermissionResultListener) {
        if (Shizuku.pingBinder()) {
            if (Shizuku.checkSelfPermission() != PackageManager.PERMISSION_GRANTED) {
                Shizuku.add  OnRequestPermissionResultListener(listener)
                Shizuku.requestPermission(SHIZUKU_PERMISSION_REQUEST_CODE)
            } else {
                // Permission already granted
                listener.onRequestPermissionResult(SHIZUKU_PERMISSION_REQUEST_CODE, PackageManager.PERMISSION_GRANTED)
            }
        } else {
            Toast.makeText(context, context.getString(R.string.shizuku_not_running), Toast.LENGTH_SHORT).show()
            listener.onRequestPermissionResult(SHIZUKU_PERMISSION_REQUEST_CODE, PackageManager.PERMISSION_DENIED)
        }
    }
}

