package com.maxtayler.cakes.ui.list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.maxtayler.cakes.data.model.Cake
import com.maxtayler.cakes.ui.components.previews.LightDarkPreviews
import com.maxtayler.cakes.ui.theme.CakesTheme

@Composable
fun CakeDescriptionDialog(
    cake: Cake,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    @OptIn(ExperimentalMaterial3Api::class)
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier
    ) {
        CakeDescriptionDialogContent(cake = cake, onClose = onDismiss)
    }
}

@Composable
private fun CakeDescriptionDialogContent(
    cake: Cake,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.sizeIn(minWidth = 280.dp, maxWidth = 560.dp),
        shape = AlertDialogDefaults.shape,
        color = AlertDialogDefaults.containerColor,
        tonalElevation = AlertDialogDefaults.TonalElevation
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = cake.title,
                style = MaterialTheme.typography.headlineSmall,
                color = AlertDialogDefaults.titleContentColor
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = cake.description,
                style = MaterialTheme.typography.bodyMedium,
                color = AlertDialogDefaults.textContentColor
            )
            Spacer(Modifier.height(24.dp))
            TextButton(
                onClick = onClose,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = "Close")
            }
        }
    }
}

@LightDarkPreviews
@Composable
private fun CakeDescriptionDialogContentPreview() {
    CakesTheme {
        CakeDescriptionDialogContent(
            cake = Cake(
                title = "Dundee Cake",
                description = "A staple with Dundonians",
                imageUrl = ""
            ),
            onClose = {}
        )
    }
}