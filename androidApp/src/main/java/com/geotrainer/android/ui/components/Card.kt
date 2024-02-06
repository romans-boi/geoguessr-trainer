package com.geotrainer.android.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.geotrainer.android.ui.components.preview.PreviewSurface
import com.geotrainer.android.ui.theme.GeoTrainerTheme

private const val previewGroup = "Card"

@Suppress("MAGIC_NUMBER")
private object CardElevations {
    private val elevation: Dp = 4.dp
    private val flat: Dp = 0.dp

    @Composable
    fun elevated(): CardElevation = CardDefaults.elevatedCardElevation(
        elevation, elevation, elevation, elevation, elevation, elevation
    )

    @Composable
    fun flat(): CardElevation = CardDefaults.elevatedCardElevation(
        flat, flat, flat, flat, flat, flat
    )

    @Composable
    fun elevationFor(isElevated: Boolean): CardElevation = when (isElevated) {
        true -> elevated()
        false -> flat()
    }
}

/**
 * Card styled with GeoTrainer colours by default. This overload allows the card to be clickable with
 * an onClick event.
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeoTrainerCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = GeoTrainerTheme.colors.White,
    contentColor: Color = GeoTrainerTheme.colors.DarkBlue,
    borderColor: Color? = null,
    cornerSize: Dp = 8.dp,
    isElevated: Boolean = true,
    content: @Composable ColumnScope.() -> Unit,
) = Card(
    onClick = onClick,
    modifier = modifier,
    shape = RoundedCornerShape(cornerSize),
    elevation = CardElevations.elevationFor(isElevated),
    colors = CardDefaults.cardColors(
        containerColor = backgroundColor,
        contentColor = contentColor,
    ),
    content = content,
    border = borderColor?.let {
        BorderStroke(1.dp, color = borderColor)
    }
)

/**
 * Card styled with GeoTrainer colours by default. This overload has no clickable functionality.
 * */
@Composable
fun GeoTrainerCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = GeoTrainerTheme.colors.White,
    contentColor: Color = GeoTrainerTheme.colors.DarkBlue,
    borderColor: Color? = null,
    cornerSize: Dp = 8.dp,
    isElevated: Boolean = true,
    content: @Composable ColumnScope.() -> Unit,
) = Card(
    modifier = modifier,
    shape = RoundedCornerShape(cornerSize),
    elevation = CardElevations.elevationFor(isElevated),
    colors = CardDefaults.cardColors(
        containerColor = backgroundColor,
        contentColor = contentColor,
    ),
    content = content,
    border = borderColor?.let {
        BorderStroke(1.dp, color = borderColor)
    }
)

@Preview(name = "GeoTrainer card", group = previewGroup)
@Composable
internal fun GeoTrainerCardPreview() = PreviewSurface {
    Column(modifier = Modifier.padding(16.dp)) {
        GeoTrainerCard {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                Text(text = "Some text", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.width(20.dp))

                Column {
                    Text(text = "Some other text", style = MaterialTheme.typography.titleSmall)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Some more text", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
