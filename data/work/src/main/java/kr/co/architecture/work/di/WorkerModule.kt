package kr.co.architecture.work.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

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