package com.skatt.calculator.calculatorkotlin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.foundation.ExperimentalFoundationApi
//import androidx.compose.material.icons.filled.Calculate
//import androidx.compose.material.icons.filled.Source

@Composable
@Preview
fun App() {
    // 1. Les variables d'état (pour l'affichage et les calculs)
    var affichage by remember { mutableStateOf("0") }
    val GrisClair = Color(0xFFA5A5A5)
    val GrisFonce = Color(0xFF333333)
    val OrangeIphone = Color(0xFFFF9F0A)
    val NoirTexte = Color.Black

    MaterialTheme {
        // 2. Le conteneur principal (toute la fenêtre)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 3. L'ÉCRAN d'affichage
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color.Black)
                    .padding(16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = affichage,
                    color = Color.White,
                    fontSize = 48.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Ligne 1 : AC +/- % Div
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Bouton("AC", GrisClair, NoirTexte) { affichage = "0" }
                Bouton("+/-", GrisClair, NoirTexte) { /* +/- */ }
                Bouton("%", GrisClair, NoirTexte) { /* % */ }
                Bouton("÷", OrangeIphone) { /* div */ }
            }
            // Ligne 2 : 7 8 9 Mult
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Bouton("7", GrisFonce) { affichage += "7" }
                Bouton("8", GrisFonce) { affichage += "8" }
                Bouton("9", GrisFonce) { affichage += "9" }
                Bouton("×", OrangeIphone) { /* mult */ }
            }
            // Ligne 3 : 4 5 6  moins
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Bouton("4", GrisFonce) { affichage += "4" }
                Bouton("5", GrisFonce) { affichage += "5" }
                Bouton("6", GrisFonce) { affichage += "6" }
                Bouton("-", OrangeIphone) { /* minus */ }
            }
            // Ligne 4 : 1 2 3 plus
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Bouton("1", GrisFonce) { affichage += "1" }
                Bouton("2", GrisFonce) { affichage += "2" }
                Bouton("3", GrisFonce) { affichage += "3" }
                Bouton("+", OrangeIphone) { /* plus */ }
            }
            // Ligne 5 : 0 . =
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Bouton("0", GrisFonce, isLarge = true) { affichage += "0" }
                Bouton(".", GrisFonce) { affichage += "." }
                Bouton("=", OrangeIphone) { /* égal */ }
            }

            Spacer(modifier = Modifier.height(10.dp))

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
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(80.dp)
            .width(if (isLarge) 170.dp else 80.dp)
            .padding(2.dp),
        shape = CircleShape,
        contentPadding = PaddingValues(0.dp), // Supprime le padding interne par défaut
        colors = ButtonDefaults.buttonColors(containerColor = couleurFond)
    ) {
        // On utilise Box pour un centrage parfait, surtout pour le "+/-"
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(
                text = texte,
                // On réduit un peu la taille pour le "+/-" s'il ne passe pas
                fontSize = if (texte == "+/-") 22.sp else (if (isLarge) 30.sp else 26.sp),
                color = couleurTexte,
                softWrap = false // Empêche le texte de passer à la ligne
            )
        }
    }
}