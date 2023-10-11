package kr.co.korean.database.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kr.co.korean.database.KoreanInvestmentDB
import kr.co.korean.database.dao.MarvelCharacterDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): KoreanInvestmentDB {
        return Room.databaseBuilder(
            appContext,
            KoreanInvestmentDB::class.java,
            "korean_investment.db"
        ).build()
    }

    @Provides
    fun provideMarvelCharacterDao(database: KoreanInvestmentDB): MarvelCharacterDao {
        return database.marvelCharacterDao()
    }
}