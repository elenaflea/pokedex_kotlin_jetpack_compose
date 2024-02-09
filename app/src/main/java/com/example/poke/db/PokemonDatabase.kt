package com.example.poke.db



import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.poke.bo.Pokemon
import com.example.poke.dao.PokemonDAO

@Database(entities = [Pokemon ::class], version = 1)
abstract class PokemonDatabase :  RoomDatabase(){


    abstract fun getArticleDAO() : PokemonDAO

    companion object {
        private var INSTANCE : PokemonDatabase? = null

        fun getInstance(context: Context) : PokemonDatabase {
            var instance = INSTANCE
            if(instance ==null) {

                instance = Room.databaseBuilder(
                    context,
                    PokemonDatabase::class.java,
                    "pokedex"
                )
                    //   .allowMainThreadQueries()
                    .fallbackToDestructiveMigration().build()

                INSTANCE = instance
            }
            return instance
        }
    }


}