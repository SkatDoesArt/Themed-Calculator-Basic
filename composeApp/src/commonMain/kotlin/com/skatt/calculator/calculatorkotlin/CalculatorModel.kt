package com.skatt.calculator.calculatorkotlin

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
// Note: Si 'mozilla' reste rouge après le Sync Gradle, essayez de redémarrer IntelliJ
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable

class CalculatorViewModel : ViewModel() {

    private val _equationText = MutableStateFlow("0")
    val equationText = _equationText.asStateFlow()

    private val _resultText = MutableStateFlow("")
    val resultText = _resultText.asStateFlow()

    fun onButtonClick(btn: String) {
        val currentEq = _equationText.value

        when (btn) {
            "AC" -> {
                _equationText.value = "0"
                _resultText.value = ""
            }
            "C" -> {
                _equationText.value = if (currentEq.length > 1) currentEq.dropLast(1) else "0"
            }
            "=" -> {
                if (_resultText.value.isNotEmpty()) {
                    _equationText.value = _resultText.value
                    _resultText.value = ""
                }
            }
            else -> {
                // Correction pour remplacer le 0
                _equationText.value = if (currentEq == "0") btn else currentEq + btn

                try {
                    val res = calculateResult(_equationText.value)
                    if (res != "undefined") _resultText.value = res
                } catch (e: Exception) { }
            }
        }
    }

    private fun calculateResult(equation: String): String {
        val context: Context = Context.enter()
        context.optimizationLevel = -1
        val scriptable: Scriptable = context.initStandardObjects()
        val fixedEquation = equation.replace("×", "*").replace("÷", "/")
        var finalResult = context.evaluateString(scriptable, fixedEquation, "Javascript", 1, null).toString()
        if (finalResult.endsWith(".0")) finalResult = finalResult.replace(".0", "")
        return finalResult
    }
}