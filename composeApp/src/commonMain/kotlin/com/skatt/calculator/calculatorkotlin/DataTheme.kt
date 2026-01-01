package com.skatt.calculator.calculatorkotlin

import androidx.compose.ui.graphics.Color
import calculatorkotlin.composeapp.generated.resources.Res
import calculatorkotlin.composeapp.generated.resources.appleBasic
import calculatorkotlin.composeapp.generated.resources.chainsawman
import calculatorkotlin.composeapp.generated.resources.cyberpunk
import calculatorkotlin.composeapp.generated.resources.dandadan
import calculatorkotlin.composeapp.generated.resources.jjk
import calculatorkotlin.composeapp.generated.resources.kuromi
import org.jetbrains.compose.resources.DrawableResource

data class Theme(
    val name : String,
    val backgroundColor: Color,
    val textColor: Color,
    val boutonCentre : Color,
    val boutonHaut : Color,
    val boutonDroite : Color,
    val themeIcon: DrawableResource)

val listTheme = listOf(
    Theme(
        name = "Apple Basic",
        backgroundColor = Color.Black,
        textColor = Color.White,
        boutonCentre = Color(0xFF333333),
        boutonHaut = Color(0xFFA5A5A5),
        boutonDroite = Color(0xFFFF9F0A),
        themeIcon = Res.drawable.appleBasic
    ),
    Theme(
        name = "DanDaDan",
        backgroundColor = Color(0xFFFF9400),
        textColor = Color.White,
        boutonCentre = Color(0xFF4A3251),
        boutonHaut = Color(0xFF7F83A9),
        boutonDroite = Color(0xFFFF4E01),
        themeIcon = Res.drawable.dandadan
    ),
    Theme(
        name = "Cyberpunk Edgerunners",
        backgroundColor = Color(0xFF0A1129),
        textColor = Color.White,
        boutonCentre = Color(0xFFAC9ACC),
        boutonHaut = Color(0xFF8FEBDC),
        boutonDroite = Color(0xFFE158B7),
        themeIcon = Res.drawable.cyberpunk
    ),
    Theme(
        name = "ChainsawMan Denji",
        backgroundColor = Color(0xFF20151A),
        textColor = Color.White,
        boutonCentre = Color(0xFF806550),
        boutonHaut = Color(0xFFDEBD90),
        boutonDroite = Color(0xFFA05F4B),
        themeIcon = Res.drawable.chainsawman
    ),
    Theme(
        name = "JJK Satoru Gojo",
        backgroundColor = Color(0xFF1F2033),
        textColor = Color.White,
        boutonCentre = Color(0xFF5793A9),
        boutonHaut = Color(0xFFCEDEF5),
        boutonDroite = Color(0xFF9C5ACD),
        themeIcon = Res.drawable.jjk
    ),
    Theme(
        name = "Kuromi",
        backgroundColor = Color.White,
        textColor = Color.Black,
        boutonCentre = Color(0xFF5B5A58),
        boutonHaut = Color(0xFFD9D9D9),
        boutonDroite = Color(0xFFE48BA9),
        themeIcon = Res.drawable.kuromi
    )
)