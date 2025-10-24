
package com.llucs.activitysend.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.llucs.activitysend.R
import com.llucs.activitysend.data.ActivityInfoModel

@Composable
fun SendScreen(
    activity: ActivityInfoModel,
    onPickFile: () -> Unit,
    onSend: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.send_to, activity.label),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(onClick = onPickFile, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.select_file))
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = onSend, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.send))
        }
    }
}

