package kr.co.architecture.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kr.co.architecture.database.ArchitectureSampleAppDB
import kr.co.architecture.database.dao.MarvelCharacterDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): ArchitectureSampleAppDB {
        return Room.databaseBuilder(
            appContext,
            ArchitectureSampleAppDB::class.java,
            "korean_investment.db"
        ).build()
    }

    @Provides
    fun provideMarvelCharacterDao(database: ArchitectureSampleAppDB): MarvelCharacterDao {
        return database.marvelCharacterDao()
    }
}