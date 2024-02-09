package com.example.poke.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.poke.PokemonViewModel
import com.example.poke.ui.common.FormTextRow


@Composable
fun PokemonFormScreen(
    modifier: Modifier = Modifier,
    pokemonViewModel: PokemonViewModel = viewModel(
        factory = PokemonViewModel.Factory
    ),
    onClickOnSave: () -> Unit
) {
    val contextForToast = LocalContext.current.applicationContext

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.verticalScroll(
            rememberScrollState()
        )
    ) {
        PokemonForm(pokemonViewModel = pokemonViewModel)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                pokemonViewModel.insertPokemon()
                //contact api pour ajouter un nouvel article
                Toast.makeText(
                    contextForToast,
                    "Sauvegarde éffectuée",
                    Toast.LENGTH_LONG
                ).show()
                onClickOnSave()
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Enregistrer")
        }
    }
}

@Composable
fun PokemonForm(
    modifier: Modifier = Modifier,
    pokemonViewModel: PokemonViewModel
) {
    Column {
        FormTextRow(
            label = "Titre",
            value = pokemonViewModel.name,
            onValueChange = {
                pokemonViewModel.name = it
            }
        )
        FormTextRow(
            label = "Sprite",
            value = pokemonViewModel.sprite,
            onValueChange = {
                pokemonViewModel.sprite = it
            }
        )


    }
}

@Preview
@Composable
fun PokemonFormPreview() {
    PokemonFormScreen() {}
}