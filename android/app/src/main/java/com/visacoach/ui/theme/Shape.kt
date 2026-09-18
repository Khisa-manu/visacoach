package com.visacoach.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val VisaCoachShapes = Shapes(
    extraSmall = RoundedCornerShape(6.dp),
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(14.dp),
    large = RoundedCornerShape(18.dp),
    extraLarge = RoundedCornerShape(24.dp)
)

val ShapeButton = RoundedCornerShape(14.dp)
val ShapeCard = RoundedCornerShape(16.dp)
val ShapeCardLg = RoundedCornerShape(20.dp)
val ShapeTextField = RoundedCornerShape(12.dp)
val ShapeChip = RoundedCornerShape(50)
val ShapeBadge = RoundedCornerShape(8.dp)

object VisaCoachShapeTokens {
    val button = ShapeButton
    val card = ShapeCard
    val cardLg = ShapeCardLg
    val textField = ShapeTextField
    val chip = ShapeChip
    val badge = ShapeBadge
}