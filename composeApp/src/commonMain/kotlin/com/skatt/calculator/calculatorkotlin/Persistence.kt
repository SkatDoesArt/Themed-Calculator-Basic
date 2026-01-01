package com.skatt.calculator.calculatorkotlin

import androidx.compose.runtime.Composable

@Composable
expect fun rememberSettings(): MySettings

interface MySettings {
    fun save(key: String, value: String)
    fun load(key: String): String
}

