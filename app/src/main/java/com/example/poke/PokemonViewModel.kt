package com.example.poke

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.poke.bo.Pokemon
import com.example.poke.bo.Stats
import com.example.poke.dao.PokemonDAO
import com.example.poke.db.PokemonDatabase
import com.example.poke.service.PokemonService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokemonViewModel(private val pokemonDAO: PokemonDAO) : ViewModel(){

    var pokemon = mutableStateOf(Pokemon())
    var pokemons = mutableStateOf<List<Pokemon>>(emptyList())
    var isLoading = mutableStateOf(false)
    var checkedFav = mutableStateOf(false)

    var name by mutableStateOf("")
    var pokedexId by mutableStateOf(0)
    var image by mutableStateOf("")
    var sprite by mutableStateOf("")
    var stats by mutableStateOf(Stats())



    fun getPokemonById(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            pokemon.value = PokemonService.PokemonApi.retrofitService.getPokemonById(id)
            isLoading.value = true
        }
    }

//    fun getPokemonList()  {
//        viewModelScope.launch(Dispatchers.IO) {
//            pokemons.postValue(PokemonService.PokemonApi.retrofitService.getAllPokemons())
//        }
//    }

    fun getPokemonList() {
        //Ajout du dispatcher.io pour lancer des appels aync qui ne bloque pas l'ui
        //permet au loading screen de fonctionner propremement
        viewModelScope.launch(Dispatchers.IO) {
            pokemons.value = PokemonService.PokemonApi.retrofitService.getAllPokemons()
            isLoading.value = true
        }
    }

    fun  getRandomPokemon() : Pokemon {
        return pokemons.value!!.random()
    }


    fun insertPokemon() {
        viewModelScope.launch(Dispatchers.IO) {
            val newPokemon = Pokemon(
                pokedexId = pokedexId,
                name = name,
                image = image,
                sprite = sprite,

            )
            PokemonService.PokemonApi.retrofitService.addPokemon(newPokemon)
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////

    fun insertPokemonFav() {
        viewModelScope.launch(Dispatchers.IO) {
            val newPokemonFav by pokemon
            pokemonDAO.insert(newPokemonFav)
        }
    }

    fun deletePokemonFav() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentPokemon by pokemon
            pokemonDAO.delete(currentPokemon)
        }
    }

    fun getPokemonFavById(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val pokemonFav = pokemonDAO.getById(id)
            if (pokemonFav != null) {
                checkedFav.value = true
            }
        }
    }

    fun getAllPokemonsFav() {
        viewModelScope.launch(Dispatchers.IO) {
            pokemons.value = pokemonDAO.getAll()
            isLoading.value = true
        }
    }



    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {

                val context = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                return PokemonViewModel(
                    PokemonDatabase.getInstance(context).getArticleDAO()
                ) as T
            }
        }
    }

}