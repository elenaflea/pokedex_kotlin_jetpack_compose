package com.example.poke.service

import com.example.poke.bo.Pokemon
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface PokemonService {

    companion object{
        val BASE_URL = "https://pokebuildapi.fr/api/v1/"

        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi)).build()


    }
    @GET("pokemon/")
    suspend fun getAllPokemons() :List<Pokemon>


    @GET("pokemon/{id}")
    suspend fun getPokemonById(@Path("id") id : Long ) :Pokemon


    @POST("pokemon/")
    suspend fun addPokemon(@Body pokemon: Pokemon)


    object PokemonApi{
        val retrofitService by lazy {
            retrofit.create(PokemonService::class.java )
        }
    }
}