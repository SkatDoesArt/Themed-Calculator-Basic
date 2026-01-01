package com.skatt.calculator.calculatorkotlin

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import org.w3c.dom.HTMLElement

@OptIn(ExperimentalComposeUiApi::class)
fun main() {

    val container = document.getElementById("compose-visual-root") as? HTMLElement

    val target = container ?: document.body ?: return

    ComposeViewport(target) {
        App()
    }
}