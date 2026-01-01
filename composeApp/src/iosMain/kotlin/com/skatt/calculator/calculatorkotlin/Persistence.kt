package com.skatt.calculator.calculatorkotlin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.Foundation.NSUserDefaults

class IosSettings : MySettings {
    override fun save(key: String, value: String) {
        NSUserDefaults.standardUserDefaults.setObject(value, key)
    }
    override fun load(key: String): String =
        NSUserDefaults.standardUserDefaults.stringForKey(key) ?: ""
}

@Composable
actual fun rememberSettings(): MySettings = remember { IosSettings() }