package kr.co.korean.database

import androidx.room.Database
import androidx.room.RoomDatabase
import kr.co.korean.database.dao.MarvelCharacterDao
import kr.co.korean.database.entity.MarvelCharacter

@Database(
    entities = [
        MarvelCharacter::class],
    version = 1
)
abstract class KoreanInvestmentDB : RoomDatabase() {
    abstract fun marvelCharacterDao() : MarvelCharacterDao

}