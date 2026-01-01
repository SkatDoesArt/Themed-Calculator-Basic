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
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.statusBarsPadding

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun App() {
    val mySettings = rememberSettings()
    val savedThemeId = remember {
        val saved = mySettings.load("selected_theme")
        if (saved.isEmpty()) 0 else saved.toIntOrNull() ?: 0
    }
    var themeId by remember { mutableIntStateOf(savedThemeId) }
    val currentTheme = listTheme[themeId]
    var affichage by remember { mutableStateOf("0") }
    var calculComplet by remember { mutableStateOf("") }
    var valeurStockee by remember { mutableStateOf<Double?>(null) }
    var operateur by remember { mutableStateOf<String?>(null) }
    var resetEcran by remember { mutableStateOf(false) }


    val savedData = remember { mySettings.load("calc_history") }
    var history by remember {
        mutableStateOf(if (savedData.isEmpty()) listOf<String>() else savedData.split("##"))
    }
    var menuHistory by remember { mutableStateOf(false) }

    val NoirTexte = Color.Black

    fun format(value: Double): String =
        if (value % 1.0 == 0.0) value.toInt().toString() else value.toString()

    val clearAll = {
        affichage = "0"
        calculComplet = ""
        valeurStockee = null
        operateur = null
        resetEcran = false
    }

    val viderHistorique = {
        history = emptyList()
        mySettings.save("calc_history", "")
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
            val calcul = "${format(a)} $operateur ${format(b)} ="
            val resultat = when (operateur) {
                "+" -> a + b
                "-" -> a - b
                "×" -> a * b
                "÷" -> if (b != 0.0) a / b else null
                else -> null
            }
            val resultatTexte = resultat?.let { format(it) } ?: "Erreur"
            calculComplet = calcul
            val newHistory = (listOf("$calcul $resultatTexte") + history).take(30)
            history = newHistory
            mySettings.save("calc_history", newHistory.joinToString("##"))
            affichage = resultatTexte
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
        Box(modifier = Modifier.fillMaxSize().background(currentTheme.backgroundColor)) {
            // --- COUCHE 1 : LA CALCULATRICE ---
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                // Barre du haut avec bouton Menu
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start,verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { menuHistory = true }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Historique",
                            tint = currentTheme.boutonDroite,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Text(
                        text = "SkatDoesCode",
                        color = currentTheme.boutonDroite.copy(alpha = 0.5f),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start,
                        maxLines = 1
                    )
                }

                Spacer(modifier = Modifier.weight(3f))

                Text(
                    text = calculComplet,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                    color = currentTheme.textColor.copy(alpha = 0.6f),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.End,
                    maxLines = 1
                )

                Text(
                    text = affichage,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                    color = currentTheme.textColor,
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.End,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.weight(1f))

                // Grille de boutons
                Column(
                    modifier = Modifier.navigationBarsPadding().padding(bottom = 15.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Ligne AC...
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        BoutonSpecial(if (affichage == "0") "AC" else "C", currentTheme.boutonHaut, Color.Black,
                            { if (affichage == "0") { valeurStockee = null; operateur = null } else  deleteLast() },
                            { affichage = "0"; valeurStockee = null; operateur = null })
                        Bouton("+/-", currentTheme.boutonHaut, Color.Black) { val v = affichage.toDoubleOrNull() ?: 0.0; affichage = format(v * -1) }
                        Bouton("%", currentTheme.boutonHaut, Color.Black) { val v = affichage.toDoubleOrNull() ?: 0.0; affichage = format(v / 100.0) }
                        Bouton("÷", currentTheme.boutonDroite) { valeurStockee = affichage.toDoubleOrNull(); operateur = "÷"; resetEcran = true }
                    }

                    // Chiffres 7-8-9, 4-5-6, 1-2-3
                    val chiffres = listOf(listOf("7","8","9","×"), listOf("4","5","6","-"), listOf("1","2","3","+"))
                    chiffres.forEach { ligne ->
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                            ligne.forEach { char ->
                                val isOp = char in listOf("×", "-", "+")
                                Bouton(char, if (isOp) currentTheme.boutonDroite else currentTheme.boutonCentre) {
                                    if (isOp) { valeurStockee = affichage.toDoubleOrNull(); operateur = char; resetEcran = true }
                                    else { if (affichage == "0" || resetEcran) { affichage = char; resetEcran = false } else affichage += char }
                                }
                            }
                        }
                    }

                    // Dernière Ligne
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        BoutonTheme(currentTheme.themeIcon, currentTheme.boutonCentre) { newIndex ->
                            themeId = newIndex
                            mySettings.save("selected_theme", newIndex.toString())
                        }
                        Bouton("0", currentTheme.boutonCentre) { if (affichage == "0" || resetEcran) { affichage = "0"; resetEcran = false } else affichage += "0" }
                        Bouton(".", currentTheme.boutonCentre) { if (!affichage.contains(".")) affichage += "." }
                        Bouton("=", currentTheme.boutonDroite) { calculer() }
                    }
                }
            }

            // --- COUCHE 2 : LE PANNEAU D'HISTORIQUE (DRAWER) ---
            if (menuHistory) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.6f))
                        .clickable { menuHistory = false }
                )

                // Le panneau coulissant
                Surface(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.75f)
                        .align(Alignment.CenterStart),
                    color = currentTheme.backgroundColor,
                    shape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp).statusBarsPadding()) {
                        IconButton(onClick = { menuHistory = false }) {
                            Icon(Icons.Default.Close, contentDescription = "Fermer", tint = currentTheme.textColor)
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Historique", color = currentTheme.textColor, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                            TextButton(onClick = viderHistorique) {
                                Text("Effacer", color = currentTheme.boutonDroite)
                            }
                        }

                        Spacer(Modifier.height(20.dp))

                        Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState())) {
                            if (history.isEmpty() || (history.size == 1 && history[0].isEmpty())) {
                                Text("Aucun historique", color = Color.Gray, fontSize = 16.sp)
                            } else {
                                history.forEach { item ->
                                    if (item.isNotEmpty()) {
                                        Text(item, color = currentTheme.textColor.copy(alpha = 0.8f), fontSize = 18.sp)
                                        HorizontalDivider(color = Color.Gray.copy(alpha = 0.3f), modifier = Modifier.padding(vertical = 15.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BoutonTheme(iconRes: org.jetbrains.compose.resources.DrawableResource, couleurFond: Color, onThemeSelected: (Int) -> Unit) {
    var menu by remember { mutableStateOf(false) }
    Box {
        Surface(onClick = { menu = true }, modifier = Modifier.size(72.dp).padding(4.dp), shape = CircleShape, color = couleurFond) {
            Box(contentAlignment = Alignment.Center) {
                Image(painter = painterResource(iconRes), contentDescription = null, modifier = Modifier.size(35.dp))
            }
        }
        DropdownMenu(expanded = menu, onDismissRequest = { menu = false }, modifier = Modifier.background(couleurFond)) {
            listTheme.forEachIndexed { index, theme ->
                DropdownMenuItem(
                    leadingIcon = { Image(painter = painterResource(theme.themeIcon), contentDescription = null, modifier = Modifier.size(30.dp)) },
                    text = { Text(theme.name, color = Color.White) },
                    onClick = { onThemeSelected(index); menu = false }
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BoutonSpecial(texte: String, couleurFond: Color, couleurTexte: Color, onClick: () -> Unit, onLongClick: () -> Unit) {
    Surface(modifier = Modifier.size(72.dp).padding(4.dp), shape = CircleShape, color = couleurFond) {
        Box(modifier = Modifier.fillMaxSize().combinedClickable(onClick = onClick, onLongClick = onLongClick), contentAlignment = Alignment.Center) {
            Text(text = texte, fontSize = 26.sp, color = couleurTexte, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun Bouton(texte: String, couleurFond: Color, couleurTexte: Color = Color.White, onClick: () -> Unit) {
    Surface(onClick = onClick, modifier = Modifier.size(72.dp).padding(4.dp), shape = CircleShape, color = couleurFond) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = texte, fontSize = if (texte == "+/-") 24.sp else 30.sp, color = couleurTexte, fontWeight = FontWeight.Medium)
        }
    }
}
