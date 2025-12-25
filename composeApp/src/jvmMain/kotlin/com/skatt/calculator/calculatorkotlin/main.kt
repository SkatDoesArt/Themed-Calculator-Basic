package com.skatt.calculator.calculatorkotlin

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    val state = rememberWindowState(
        width = 400.dp,
        height = 600.dp
    )
    Window(
        onCloseRequest = ::exitApplication,
        state = state,
        title = "Calculator",
        resizable = false
    ) {
        App()
    }
}