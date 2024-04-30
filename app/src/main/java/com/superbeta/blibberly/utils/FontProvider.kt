package com.superbeta.blibberly.utils

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.superbeta.blibberly.R

object FontProvider {

    private val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )

    private val poppins = GoogleFont("Poppins")

    val poppinsFontFamily = FontFamily(
        Font(googleFont = poppins, fontProvider = provider)
    )
}