package kr.co.architecture.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kr.co.architecture.database.entity.MarvelCharacter

@Dao
interface MarvelCharacterDao {

    @Query("SELECT * FROM MarvelCharacter")
    fun getAllCharacter(): Flow<List<MarvelCharacter>>

    @Query("DELETE FROM MarvelCharacter WHERE characterId = :id")
    fun deleteCharacter(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(vararg marvelCharacter: MarvelCharacter)

}