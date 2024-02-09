package com.example.poke.bo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import java.util.Date

@Entity
data class Pokemon(
    @PrimaryKey(false)
    var id : Long = 0,
    var pokedexId: Int = 0,
    var name : String ="",
    var image : String ="",
    var sprite : String ="",
//    var stats: Stats = Stats()
)
