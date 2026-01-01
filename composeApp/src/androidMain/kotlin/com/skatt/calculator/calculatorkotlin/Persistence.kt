package com.skatt.calculator.calculatorkotlin

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

class AndroidSettings(context: Context) : MySettings {
    private val prefs = context.getSharedPreferences("calculatrice_prefs", Context.MODE_PRIVATE)
    override fun save(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }
    override fun load(key: String): String = prefs.getString(key, "") ?: ""
}

@Composable
actual fun rememberSettings(): MySettings {
    val context = LocalContext.current
    return remember { AndroidSettings(context) }
}