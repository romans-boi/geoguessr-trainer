@file:Suppress("MAGIC_NUMBER")

package com.geotrainer.android.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.geotrainer.android.R

// Set of Material typography styles
internal val Typography = Typography(
    headlineLarge = GeoTrainerTypography.headlineLarge,
    headlineSmall = GeoTrainerTypography.headlineSmall,
    titleLarge = GeoTrainerTypography.titleLarge,
    titleMedium = GeoTrainerTypography.titleMedium,
    titleSmall = GeoTrainerTypography.titleSmall,
    bodyLarge = GeoTrainerTypography.bodyLarge,
    bodyMedium = GeoTrainerTypography.bodyMedium,
    bodySmall = GeoTrainerTypography.bodySmall,
    labelLarge = GeoTrainerTypography.labelLarge,
    labelMedium = GeoTrainerTypography.labelMedium,
    labelSmall = GeoTrainerTypography.labelSmall

)

internal object GeoTrainerTypography {
    private val fontFamilyRoboto = FontFamily(
        Font(R.font.roboto_regular, FontWeight.Normal, FontStyle.Normal),
        Font(R.font.roboto_italic, FontWeight.Normal, FontStyle.Italic),
        Font(R.font.roboto_bold, FontWeight.Bold, FontStyle.Normal),
        Font(R.font.roboto_bold_italic, FontWeight.Bold, FontStyle.Italic),
        Font(R.font.roboto_medium, FontWeight.Medium, FontStyle.Normal),
        Font(R.font.roboto_medium_italic, FontWeight.Medium, FontStyle.Italic),
    )
    val headlineLarge = TextStyle(
        fontFamily = fontFamilyRoboto,
        fontWeight = FontWeight.Normal,
        fontSize = 34.sp,
        lineHeight = 41.sp,
    )
    val headlineSmall = TextStyle(
        fontFamily = fontFamilyRoboto,
        fontWeight = FontWeight.Medium,
        fontSize = 17.sp,
        lineHeight = 22.sp,
    )
    val titleLarge = TextStyle(
        fontFamily = fontFamilyRoboto,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 34.sp,
    )
    val titleMedium = TextStyle(
        fontFamily = fontFamilyRoboto,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
    )
    val titleSmall = TextStyle(
        fontFamily = fontFamilyRoboto,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 24.sp,
    )
    val bodyLarge = TextStyle(
        fontFamily = fontFamilyRoboto,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 22.sp,
    )
    val bodyMedium = TextStyle(
        fontFamily = fontFamilyRoboto,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 21.sp,
    )
    val bodySmall = TextStyle(
        fontFamily = fontFamilyRoboto,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 18.sp,
    )
    val labelLarge = TextStyle(
        fontFamily = fontFamilyRoboto,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 15.sp,
    )
    val labelMedium = TextStyle(
        fontFamily = fontFamilyRoboto,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 14.sp,
    )
    val labelSmall = TextStyle(
        fontFamily = fontFamilyRoboto,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        lineHeight = 13.sp,
    )
}
