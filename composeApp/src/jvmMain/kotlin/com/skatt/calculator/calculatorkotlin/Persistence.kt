package com.skatt.calculator.calculatorkotlin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.util.prefs.Preferences

class JvmSettings : MySettings {
    private val prefs = Preferences.userNodeForPackage(JvmSettings::class.java)
    override fun save(key: String, value: String) = prefs.put(key, value)
    override fun load(key: String): String = prefs.get(key, "")
}

@Composable
actual fun rememberSettings(): MySettings = remember { JvmSettings() }