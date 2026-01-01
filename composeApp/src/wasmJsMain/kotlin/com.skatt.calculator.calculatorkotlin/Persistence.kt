package com.skatt.calculator.calculatorkotlin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.browser.localStorage

@Composable
actual fun rememberSettings(): MySettings = remember {
    object : MySettings {
        override fun save(key: String, value: String) {
            localStorage.setItem(key, value)
        }
        override fun load(key: String): String =
            localStorage.getItem(key) ?: ""
    }
}