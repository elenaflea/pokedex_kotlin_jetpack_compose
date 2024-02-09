package com.example.poke.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.poke.PokemonViewModel
import com.example.poke.bo.Pokemon
import com.example.poke.ui.common.LoadingScreen


@Composable
fun PokemonDetailScreen(
    modifier: Modifier = Modifier,
    pokemonId: Long,
    pokemonViewModel: PokemonViewModel = viewModel(
        factory = PokemonViewModel.Factory
    )
) {
    LaunchedEffect(Unit) {
        pokemonViewModel.getPokemonById(pokemonId)
        pokemonViewModel.getPokemonFavById(pokemonId)
    }

    val pokemon by pokemonViewModel.pokemon
    val isLoading by pokemonViewModel.isLoading
    var checkedFav by pokemonViewModel.checkedFav

    if (!isLoading) {
        LoadingScreen()
    } else {
        Column(
            modifier = modifier.verticalScroll(
                rememberScrollState()
            )
        ) {
            PokemonDetail(
                pokemon = pokemon,
                checkedFav = checkedFav,
                onCheckedChange = {
                    checkedFav = it
                    if (checkedFav) {
                        pokemonViewModel.insertPokemonFav()
                    } else {
                        pokemonViewModel.deletePokemonFav()
                    }
                }
            )
        }
    }
}

@Composable
fun PokemonDetail(
    pokemon: Pokemon,
    checkedFav: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = pokemon.name,
            style = MaterialTheme.typography.titleMedium,
            fontSize = 30.sp,
            modifier = Modifier
                .padding(16.dp)
                .clickable {
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.fr/search?q=eni+shop+${pokemon.name}")
                    ).also { context.startActivity(it) }
                },
            textAlign = TextAlign.Justify,
            lineHeight = 1.em
        )
        AsyncImage(
            model = pokemon.image, contentDescription = null,
            modifier = Modifier.size(200.dp)
        )
        Text(
            text = pokemon.sprite,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Justify
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Checkbox(checked = checkedFav, onCheckedChange = onCheckedChange)
            Text("Favoris ?")
        }

    }

}

@Composable
@Preview
fun PokemonDetailPreview() {
    PokemonDetail(
        pokemon = Pokemon(
            image = "data:image/png;base64,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",
            name = "Pikachu",
            sprite = "bla bla"

        ),
        checkedFav = false
    ) {

    }
}