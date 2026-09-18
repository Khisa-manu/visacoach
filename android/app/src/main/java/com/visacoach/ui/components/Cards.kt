package com.visacoach.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.visacoach.ui.theme.Dimens
import com.visacoach.ui.theme.Neutral200
import com.visacoach.ui.theme.ShapeCard
import com.visacoach.ui.theme.ShapeCardLg

@Composable
fun VisaCard(
    modifier: Modifier = Modifier,
    containerColor: Color = Color.White,
    accentColor: Color? = null,
    headerBadge: (@Composable () -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = ShapeCard,
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = BorderStroke(1.dp, accentColor ?: Neutral200),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(Dimens.cardPadding)) {
            headerBadge?.let {
                it()
                Spacer(modifier = Modifier.height(Dimens.space12))
            }
            content()
        }
    }
}

@Composable
fun VisaCardLg(
    modifier: Modifier = Modifier,
    containerColor: Color = Color.White,
    accentColor: Color? = null,
    headerBadge: (@Composable () -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = ShapeCardLg,
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = BorderStroke(1.dp, accentColor ?: Neutral200),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(Dimens.cardPaddingLg)) {
            headerBadge?.let {
                it()
                Spacer(modifier = Modifier.height(Dimens.space12))
            }
            content()
        }
    }
}