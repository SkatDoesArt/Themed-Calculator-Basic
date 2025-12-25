package com.skatt.calculator.calculatorkotlin

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform