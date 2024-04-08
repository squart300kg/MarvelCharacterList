package kr.co.architecture.database

import androidx.room.Database
import androidx.room.RoomDatabase
import kr.co.architecture.database.dao.MarvelCharacterDao
import kr.co.architecture.database.entity.MarvelCharacter

@Database(
    entities = [
        MarvelCharacter::class],
    version = 1
)
abstract class ArchitectureSampleAppDB : RoomDatabase() {
    abstract fun marvelCharacterDao() : MarvelCharacterDao

}