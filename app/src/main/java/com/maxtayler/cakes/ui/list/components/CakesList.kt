package com.maxtayler.cakes.ui.list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.maxtayler.cakes.data.model.Cake
import com.maxtayler.cakes.ui.components.previews.LightDarkPreviews
import com.maxtayler.cakes.ui.theme.CakesTheme

@Composable
fun CakesList(
    cakes: List<Cake>,
    onCakeClicked: (cake: Cake) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(cakes) { index, cake ->
            if (index > 0) HorizontalDivider()
            CakeItem(
                cake = cake,
                onCakeClicked = onCakeClicked,
                modifier = Modifier.animateItem()
            )
        }
    }
}

@Composable
private fun CakeItem(
    cake: Cake,
    onCakeClicked: (cake: Cake) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = { onCakeClicked.invoke(cake) })
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        AsyncImage(
            model = cake.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceContainerHighest),
            error = ColorPainter(MaterialTheme.colorScheme.surfaceContainerHighest),
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Spacer(Modifier.width(16.dp))
        Text(
            text = cake.title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@LightDarkPreviews
@Composable
private fun CakesListPreview() {
    CakesTheme {
        Surface {
            CakesList(
                cakes = listOf(
                    Cake(
                        title = "Dundee Cake",
                        description = "A staple with Dundonians",
                        imageUrl = ""
                    )
                ),
                onCakeClicked = { /* no-op */ })
        }
    }
}