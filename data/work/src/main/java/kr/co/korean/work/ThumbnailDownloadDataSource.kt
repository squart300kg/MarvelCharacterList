package kr.co.korean.work

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ThumbnailDownloadDataSource @Inject constructor(
    @ApplicationContext private val applicationContext: Context
) {

    fun downloadThumbnail(url: String) {
        val workParams = Data.Builder()
            .putString("url", url)
            .build()
        val workRequest = OneTimeWorkRequestBuilder<ThumbnailDownLoadWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setInputData(workParams)
            .build()
        WorkManager.getInstance(applicationContext)
            .enqueue(workRequest)
    }
}