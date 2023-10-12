package kr.co.korean.work

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext

@HiltWorker
class ThumbnailDownLoadWorker @AssistedInject constructor(
    @Assisted applicationContext: Context,
    @Assisted private val workerParams: WorkerParameters,
): CoroutineWorker(applicationContext, workerParams) {
    override suspend fun doWork(): Result {
        repeat(10) {
            Thread.sleep(1000L)
            val url = workerParams.inputData.getString("url")
            Log.e("workTest", "tick ${url}")
        }


        return Result.success()
    }
}