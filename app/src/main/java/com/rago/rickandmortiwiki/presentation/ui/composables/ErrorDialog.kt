package com.rago.rickandmortiwiki.presentation.ui.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.rago.rickandmortiwiki.R

@Composable
fun ErrorDialog(
    errorMsg: String,
    action: () -> Unit
) {
    if (errorMsg.isNotEmpty() && errorMsg.isNotBlank()) {
        Dialog(onDismissRequest = action) {
            ErrorDialogContent(errorMsg, action)
        }
    }
}

@Composable
fun ErrorDialog(
    @StringRes errorRes: Int,
    action: () -> Unit
) {
    if (errorRes != -1 && errorRes != 0) {
        Dialog(onDismissRequest = action) {
            ErrorDialogContent(stringResource(id = errorRes), action)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorDialogContent(
    body: String = stringResource(id = R.string.error_server),
    onAction: () -> Unit = {}
) {
    ElevatedCard(
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.attention),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = body,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = onAction) {
                Text(text = "OK")
            }
        }
    }
}