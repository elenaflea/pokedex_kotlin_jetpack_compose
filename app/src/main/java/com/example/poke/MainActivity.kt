package com.example.poke

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.poke.bo.Pokemon
import com.example.poke.ui.common.TitleApp
import com.example.poke.ui.screen.PokemonDetailScreen
import com.example.poke.ui.screen.PokemonFormScreen
import com.example.poke.ui.screen.PokemonListScreen
import com.example.poke.ui.theme.PokeTheme

class MainActivity : ComponentActivity() {

    lateinit var vm : PokemonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     //   vm = ViewModelProvider(this)[PokemonListViewModel::class.java]
       // vm.getPokemonList()

        setContent {
            PokeApp()
            }
        }
    }


@Composable
fun PokeApp(modifier: Modifier = Modifier) {
    PokeTheme {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = { PokeNavigationBar(navController = navController) },
            //topBar = { EniShopNavigationBar(navController = navController) }
        ) {
            Column(modifier = Modifier.padding(it)) {
                TitleApp()
                PokeNavHost(navController = navController)
            }
        }
    }
}

@Composable
fun PokeNavigationBar(navController: NavHostController) {

    var homeSelected by remember {
        mutableStateOf(true)
    }
    var favSelected by remember {
        mutableStateOf(false)
    }

    NavigationBar(modifier = Modifier.heightIn(max = 40.dp)) {
        NavigationBarItem(
            selected = homeSelected,
            onClick = {
                homeSelected = true
                favSelected = false
                navController.navigate(PokemonHome.route) {
                    launchSingleTop = true
                    popUpTo(navController.graph.findStartDestination().id)
                }
            },
            icon = {
                Icon(imageVector = PokemonHome.icon, contentDescription = null)
            }
        )
        NavigationBarItem(
            selected = favSelected,
            onClick = {
                favSelected = true
                homeSelected = false
                navController.navigate("${PokemonHome.route}?${PokemonHome.homeArg}=$favSelected") {
                    launchSingleTop = true
                    popUpTo(navController.graph.findStartDestination().id)
                }
            },
            icon = {
                Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = null)
            }
        )
    }
}

@Composable
fun PokeNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = PokemonHome.routeWithArgs
    ) {
        this.composable(
            route = PokemonHome.routeWithArgs,
            arguments = PokemonHome.arguments
        ) {
            //récupération du paramètre facultatif de la page d'accueil pour accéder aux favoris
            val fav = it.arguments?.getBoolean(PokemonHome.homeArg) ?: false

            PokemonListScreen(
                onClickOnAddArticle = {
                    navController.navigate(PokemonAdd.route) {
                        launchSingleTop = true
                        popUpTo(navController.graph.findStartDestination().id)
                    }
                },
                onClickOnArticleItem = {
                    navController.navigate("${PokemonDetail.route}/$it")
                },
                isArticlesFav = fav
            )
        }
        this.composable(PokemonAdd.route) {
            PokemonFormScreen(onClickOnSave = {
                navController.navigate(PokemonHome.route) {
                    launchSingleTop = true
                    popUpTo(navController.graph.findStartDestination().id)
                }
            })
        }
        this.composable(
            route = PokemonDetail.routeWithArgs,
            arguments = PokemonDetail.arguments
        ) {
            val pokemonId = it.arguments?.getInt(PokemonDetail.articleDetailArg) ?: 0
            PokemonDetailScreen(pokemonId = pokemonId.toLong())
        }
    }
}



///////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////

@Composable
fun ListItems(modifier: Modifier = Modifier.padding(20.dp)) {
    val list = List<Pokemon>(5) { Pokemon() }

    LazyColumn {

        items(list) {
            Item(text = "Pokemon $")
        }
    }

}

@Composable
fun Item(modifier: Modifier = Modifier, text: String) {

    var isClicked by remember {
        mutableStateOf(false)
    }
    val bgColor by animateColorAsState(
        if (isClicked) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.background
        }
    )

    var img : String  = "https://www.vsenavareni.cz/upload/img/0000823db1/pic102.jpg"

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(4.dp)
            .background(bgColor)
            .clickable {
                isClicked = !isClicked
            }
            .fillMaxWidth()
    ) {
        Image(
            modifier = Modifier
                .size(100.dp)
                .border(1.5.dp, MaterialTheme.colorScheme.secondary, CircleShape)
                .padding(10.dp),
            //imageVector = Icons.Default.Home,
            contentDescription = "Pokemon",
            painter = painterResource(id = R.drawable.pika),
        )
        Spacer(modifier = Modifier.width(4.dp))
        Par(text = text)

    }

}

@Composable
fun Par(modifier: Modifier = Modifier, text: String) {

    Text(
        modifier = modifier.padding(4.dp),
        textAlign = TextAlign.Justify,
        color = MaterialTheme.colorScheme.error,
        maxLines = 5,
        text = text
    )
}



@Composable
@Preview
fun preview() {

}



