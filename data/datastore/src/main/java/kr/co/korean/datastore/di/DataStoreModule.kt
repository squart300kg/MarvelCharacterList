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
import kr.co.korean.datastore.AppState
import kr.co.korean.datastore.AppStateSerializer
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideAppStateDataStore(
        @ApplicationContext context: Context,
        serializer: AppStateSerializer
    ): DataStore<AppState> =
        DataStoreFactory.create(
            serializer = serializer
        ) {
            context.dataStoreFile("app_state.pb")
        }
}