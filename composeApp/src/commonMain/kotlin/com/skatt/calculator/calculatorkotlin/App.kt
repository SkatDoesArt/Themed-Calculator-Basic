package com.skatt.calculator.calculatorkotlin

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun App() {
    var affichage by remember { mutableStateOf("0") }
    var valeurStockee by remember { mutableStateOf<Double?>(null) }
    var operateur by remember { mutableStateOf<String?>(null) }
    var resetEcran by remember { mutableStateOf(false) }

    val GrisClair = Color(0xFFA5A5A5)
    val GrisFonce = Color(0xFF333333)
    val OrangeIphone = Color(0xFFFF9F0A)
    val NoirTexte = Color.Black

    fun format(value: Double): String =
        if (value % 1.0 == 0.0) value.toInt().toString() else value.toString()

    val clearAll = {
        affichage = "0"
        valeurStockee = null
        operateur = null
        resetEcran = false
    }

    val deleteLast = {
        if (affichage != "0") {
            affichage = if (affichage.length > 1) affichage.dropLast(1) else "0"
        }
    }

    fun calculer() {
        val a = valeurStockee
        val b = affichage.toDoubleOrNull()
        if (a != null && b != null && operateur != null) {
            val resultat = when (operateur) {
                "+" -> a + b
                "-" -> a - b
                "×" -> a * b
                "÷" -> if (b != 0.0) a / b else null
                else -> null
            }
            affichage = resultat?.let { format(it) } ?: "Erreur"
            valeurStockee = null
            operateur = null
            resetEcran = true
        }
    }

    fun onChiffreClick(chiffre: String) {
        if (affichage == "0" || resetEcran) {
            affichage = chiffre
            resetEcran = false
        } else {
            affichage += chiffre
        }
    }

    fun onOpClick(op: String) {
        valeurStockee = affichage.toDoubleOrNull()
        operateur = op
        resetEcran = true
    }

    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize().background(Color.Black).padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            // ECRAN
            Box(
                modifier = Modifier.fillMaxWidth().weight(1f).padding(20.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Text(text = affichage, color = Color.White, fontSize = 70.sp, fontWeight = FontWeight.Light)
            }

            // LIGNE 1
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                val labelAction = if (affichage == "0") "AC" else "C"

                BoutonSpecial(
                    texte = labelAction,
                    couleurFond = GrisClair,
                    couleurTexte = NoirTexte,
                    onClick = { if (affichage == "0") clearAll() else deleteLast() },
                    onLongClick = { clearAll() }
                )

                Bouton("+/-", GrisClair, NoirTexte) {
                    val v = affichage.toDoubleOrNull() ?: 0.0
                    if (v != 0.0) affichage = format(v * -1)
                }
                Bouton("%", GrisClair, NoirTexte) {
                    val v = affichage.toDoubleOrNull() ?: 0.0
                    affichage = format(v / 100.0)
                }
                Bouton("÷", OrangeIphone) { onOpClick("÷") }
            }

            Spacer(Modifier.height(10.dp))

            // LIGNES 2, 3, 4
            val lignes = listOf(
                listOf("7", "8", "9", "×"),
                listOf("4", "5", "6", "-"),
                listOf("1", "2", "3", "+")
            )

            lignes.forEach { ligne ->
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    ligne.forEach { char ->
                        val isOp = char in listOf("×", "-", "+")
                        Bouton(char, if (isOp) OrangeIphone else GrisFonce) {
                            if (isOp) onOpClick(char) else onChiffreClick(char)
                        }
                    }
                }
                Spacer(Modifier.height(10.dp))
            }

            // DERNIERE LIGNE
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Bouton("0", GrisFonce, isLarge = true) { onChiffreClick("0") }
                Bouton(".", GrisFonce) { if (!affichage.contains(".")) affichage += "." }
                Bouton("=", OrangeIphone) { calculer() }
            }
            Spacer(Modifier.height(20.dp))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BoutonSpecial(
    texte: String,
    couleurFond: Color,
    couleurTexte: Color,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .size(80.dp)
            .padding(4.dp),
        shape = CircleShape,
        color = couleurFond
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .combinedClickable(
                    onClick = onClick,
                    onLongClick = onLongClick
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = texte,
                fontSize = 26.sp,
                color = couleurTexte,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun Bouton(
    texte: String,
    couleurFond: Color,
    couleurTexte: Color = Color.White,
    isLarge: Boolean = false,
    onClick: () -> Unit
) {
    // Utilisation de Surface + clickable pour un meilleur contrôle du centrage
    Surface(
        onClick = onClick,
        modifier = Modifier
            .height(80.dp)
            .width(if (isLarge) 170.dp else 80.dp)
            .padding(4.dp),
        shape = CircleShape,
        color = couleurFond
    ) {
        Box(
            contentAlignment = Alignment.Center, // Centrage parfait du +/- et des chiffres
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = texte,
                fontSize = if (texte == "+/-") 24.sp else 30.sp,
                color = couleurTexte,
                fontWeight = FontWeight.Medium
            )
        }
    }
}