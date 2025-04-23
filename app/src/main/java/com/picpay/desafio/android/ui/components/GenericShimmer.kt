package com.picpay.desafio.android.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.picpay.desafio.android.ui.AppTheme
import com.valentinilk.shimmer.shimmer


@Composable
fun GenericShimmer(
    modifier: Modifier = Modifier,
    cornerShape: Dp,
    height: Dp,
    cardColors: Color = MaterialTheme.colorScheme.inversePrimary,
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(cornerShape))
            .fillMaxWidth()
            .shimmer(),
        colors = CardDefaults.cardColors(containerColor = cardColors)
    ) {
        Spacer(
            modifier = Modifier
                .height(height)
        )
    }
}

@Preview
@Composable
fun ShimmerPreview() {
    AppTheme {
        GenericShimmer(
            modifier = Modifier
                .fillMaxWidth()
                .height(135.dp),
            cornerShape = 16.dp,
            height = 40.dp,
        )
    }
}
