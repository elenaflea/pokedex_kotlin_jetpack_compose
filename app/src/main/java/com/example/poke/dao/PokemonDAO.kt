package com.example.poke.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.poke.bo.Pokemon

@Dao
interface PokemonDAO {
    @Query("SELECT * FROM Pokemon WHERE id = :id")
    fun getById(id :Long) : Pokemon?

    @Insert
    fun insert(pokemon: Pokemon) : Long

    @Query("SELECT * FROM Pokemon")
    fun getAll(): List<Pokemon>

    @Update
    fun update(pokemon: Pokemon)

    @Delete
    fun delete(pokemon: Pokemon)

}