package kr.co.korean.work.di

import android.content.Context
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kr.co.korean.work.ThumbnailDownloadDataSource
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object WorkerModule {

//    @Provides
//    @Singleton
//    fun provideThumbnailDownloadDataSource(
//        worker: WorkManager,
//        @ApplicationContext context: Context
//    ): ThumbnailDownloadDataSource {
//        return ThumbnailDownloadDataSource(
//
//        )
//    }
}