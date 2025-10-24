
package com.llucs.activitysend.core

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.llucs.activitysend.R

object IntentSender {

    fun sendFileToActivity(context: Context, fileUri: Uri, component: ComponentName) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "*/*"
            putExtra(Intent.EXTRA_STREAM, fileUri)
            this.component = component
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, context.getString(R.string.error_sending_file, e.message), Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }
}

