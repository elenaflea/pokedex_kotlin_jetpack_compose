package com.example.poke

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface Destination {
    val icon: ImageVector
    val route: String
}

object PokemonHome : Destination {
    override val icon = Icons.Default.Home
    override val route = "home"

    val homeArg = "fav"
    val arguments = listOf(
        navArgument(homeArg) {
            defaultValue = false
            type = NavType.BoolType
        }
    )
    val routeWithArgs = "$route?$homeArg={$homeArg}"
}

object PokemonAdd : Destination {
    override val icon = Icons.Default.Add
    override val route = "pokemon_add"
}

object PokemonDetail : Destination {
    override val icon = Icons.Default.Dashboard
    override val route = "pokemon_detail"

    val articleDetailArg = "pokemon_id"
    val arguments = listOf(
        navArgument(articleDetailArg) { type = NavType.IntType }
    )
    val routeWithArgs = "${route}/{${articleDetailArg}}"
}