package com.superbeta.blibberly.ui

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)


val ColorPrimary = Color(0xff9736FF)
val ColorSecondary = Color(0xFF407EF6)
val ColorTertiary = Color(0xFFB0A4FD)

val ColorDisabled = Color(0xffd3d3d3)
val WhiteWithAlpha = Color.White.copy(alpha = 0.15f)

val TextColorGrey = Color(0xFF3C3C3C)

enum class BLIBMOJI_BG_COLORS {
    BLUE,
    WHITE,
    RED,
    GRAY,
    CYAN,
    BLACK,
    DARKGRAY,
    GREEN,
    MAGENTA,
    YELLOW

}


val avatarBGColorsMap = mapOf(
    BLIBMOJI_BG_COLORS.BLUE.toString() to Color.Blue,
    BLIBMOJI_BG_COLORS.WHITE.toString() to Color.White,
    BLIBMOJI_BG_COLORS.RED.toString() to Color.Red,
    BLIBMOJI_BG_COLORS.GRAY.toString() to Color.Gray,
    BLIBMOJI_BG_COLORS.CYAN.toString() to Color.Cyan,
    BLIBMOJI_BG_COLORS.BLACK.toString() to Color.Black,
    BLIBMOJI_BG_COLORS.DARKGRAY.toString() to Color.DarkGray,
    BLIBMOJI_BG_COLORS.GREEN.toString() to Color.Green,
    BLIBMOJI_BG_COLORS.MAGENTA.toString() to Color.Magenta,
    BLIBMOJI_BG_COLORS.YELLOW.toString() to Color.Yellow
)