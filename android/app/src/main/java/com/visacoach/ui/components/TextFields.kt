package com.visacoach.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.visacoach.ui.theme.*

/**
 * USA VisaCoach - Production Reusable Text Field
 * Standardized OutlinedTextField with precise border colors, focus states, and typography.
 */
@Composable
fun VisaTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    supportingText: String? = null,
    isError: Boolean = false,
    errorMessage: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else 4,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    shape: Shape = ShapeTextField
) {
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            readOnly = readOnly,
            singleLine = singleLine,
            maxLines = maxLines,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            shape = shape,
            textStyle = VisaCoachTypography.bodyLarge.copy(
                color = if (enabled) PrimaryNavy else Neutral400
            ),
            label = label?.let {
                {
                    Text(
                        text = it,
                        style = VisaCoachTypography.bodyMedium
                    )
                }
            },
            placeholder = placeholder?.let {
                {
                    Text(
                        text = it,
                        style = VisaCoachTypography.bodyLarge.copy(
                            color = Neutral400
                        )
                    )
                }
            },
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            isError = isError,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = PrimaryNavy,
                unfocusedTextColor = PrimaryNavy,
                disabledTextColor = Neutral400,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedBorderColor = PrimaryNavy,
                unfocusedBorderColor = Neutral300,
                disabledBorderColor = Neutral200,
                errorBorderColor = Error,
                focusedLabelColor = PrimaryNavy,
                unfocusedLabelColor = Neutral600,
                errorLabelColor = Error,
                cursorColor = PrimaryNavy
            )
        )

        // Helper or Error Text
        val displayedError = if (isError && !errorMessage.isNullOrBlank()) errorMessage else null
        val displayedSupport = if (!isError && !supportingText.isNullOrBlank()) supportingText else null

        AnimatedVisibility(visible = displayedError != null || displayedSupport != null) {
            Text(
                text = displayedError ?: (displayedSupport ?: ""),
                style = VisaCoachTypography.bodySmall,
                color = if (displayedError != null) Error else Neutral600,
                modifier = Modifier.padding(start = 12.dp, top = 4.dp, end = 12.dp)
            )
        }
    }
}

/**
 * USA VisaCoach - Kenyan M-Pesa Phone Number Input
 * Pre-styled for Safaricom M-Pesa numbers (e.g., 0712345678 or 254712345678).
 */
@Composable
fun VisaMpesaPhoneField(
    phone: String,
    onPhoneChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String? = null,
    enabled: Boolean = true
) {
    VisaTextField(
        value = phone,
        onValueChange = { input ->
            // Filter digits only and limit max length to 12
            val digits = input.filter { it.isDigit() }
            if (digits.length <= 12) {
                onPhoneChange(digits)
            }
        },
        modifier = modifier,
        label = "M-Pesa Phone Number",
        placeholder = "0712 345 678",
        leadingIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 12.dp, end = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "M-Pesa Phone",
                    tint = SafaricomGreen,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Surface(
                    shape = ShapeChip,
                    color = SafaricomGreen.copy(alpha = 0.12f)
                ) {
                    Text(
                        text = "+254",
                        style = VisaCoachTypography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = SafaricomGreen,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        },
        supportingText = "You will receive an instant Lipa na M-Pesa PIN prompt.",
        isError = isError,
        errorMessage = errorMessage ?: "Enter a valid 10-digit Kenyan mobile number (e.g. 07XXXXXXXX)",
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        enabled = enabled
    )
}
