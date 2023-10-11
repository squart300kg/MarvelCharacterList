package kr.co.korean.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kr.co.korean.datastore.MarvelCharacterSerializer
import kr.co.korean.datastore.MarvelCharacters
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideMarvelCharacterDataStore(
        @ApplicationContext context: Context,
        marvelCharacterSerializer: MarvelCharacterSerializer
    ): DataStore<MarvelCharacters> =
        DataStoreFactory.create(
            serializer = marvelCharacterSerializer
        ) {
            context.dataStoreFile("marvel_character.pb")
        }
}