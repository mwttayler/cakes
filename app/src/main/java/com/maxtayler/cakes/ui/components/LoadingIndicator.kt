package com.maxtayler.cakes.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maxtayler.cakes.ui.components.previews.LightDarkPreviews
import com.maxtayler.cakes.ui.theme.CakesTheme

@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        CircularProgressIndicator(
            strokeWidth = 6.dp,
            modifier = Modifier.size(48.dp)
        )
    }
}

@LightDarkPreviews
@Composable
private fun LoadingIndicatorPreview() {
    CakesTheme {
        Surface {
            LoadingIndicator(modifier = Modifier.fillMaxSize())
        }
    }
}