package com.github.rllsh57.rickandmorty.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.rllsh57.rickandmorty.data.database.entity.CharacterEntity

@Dao
interface CharacterDao {

    @Query("SELECT COUNT(*) FROM characters")
    suspend fun getCount(): Int

    @Query("SELECT * FROM characters LIMIT :limit OFFSET :offset")
    suspend fun getCharacters(offset: Int, limit: Int): List<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCharacters(characters: List<CharacterEntity>)
}