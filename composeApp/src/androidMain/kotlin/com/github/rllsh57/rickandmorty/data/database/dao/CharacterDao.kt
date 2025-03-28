package com.github.rllsh57.rickandmorty.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.rllsh57.rickandmorty.data.database.entity.CharacterEntity

@Dao
interface CharacterDao {

    @Query("SELECT * FROM characters")
    suspend fun getCharacters(): List<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCharacters(countries: List<CharacterEntity>)
}