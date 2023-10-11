package kr.co.korean.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kr.co.korean.database.entity.MarvelCharacter

@Dao
interface MarvelCharacterDao {

    @Query("SELECT * FROM MarvelCharacter")
    fun getAllCharacter(): Flow<List<MarvelCharacter>>

    @Query("DELETE FROM MarvelCharacter WHERE characterId = :id")
    fun deleteCharacter(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(vararg marvelCharacter: MarvelCharacter)

}