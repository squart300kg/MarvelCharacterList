package kr.co.korean.work

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.Operation
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.launch
import javax.inject.Inject
import kr.co.korean.common.model.Result
import java.lang.Exception

sealed interface ImageDownLoadResult {
    object Success: ImageDownLoadResult
    object Loading: ImageDownLoadResult
    object NoneStart: ImageDownLoadResult
    object Error: ImageDownLoadResult
}

class ThumbnailDownloadDataSource @Inject constructor(
    @ApplicationContext private val applicationContext: Context
) {

    private val _imageDownloadState = MutableStateFlow<ImageDownLoadResult>(ImageDownLoadResult.NoneStart)
    @OptIn(FlowPreview::class)
    val imageDownloadState
        get() = _imageDownloadState.asStateFlow()
            .debounce(100L)

//            .distinctUntilChangedBy {
//                ImageDownLoadResult.Loading
//            }


    fun downloadThumbnail(url: String) {
        val workParams = Data.Builder()
            .putString("url", url)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<ThumbnailDownLoadWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setInputData(workParams)
            .build()

        WorkManager.getInstance(applicationContext)
            .apply {
                getWorkInfoByIdLiveData(workRequest.id)
                    .apply {
                        observeForever { workInfo ->
                            CoroutineScope(Dispatchers.Default).launch {
                                if (workInfo.state.isFinished) {
                                    _imageDownloadState.emit(ImageDownLoadResult.Success)
                                } else if (workInfo.state == WorkInfo.State.RUNNING) {
                                    _imageDownloadState.emit(ImageDownLoadResult.Loading)
                                } else if (workInfo.state == WorkInfo.State.FAILED) {
                                    _imageDownloadState.emit(ImageDownLoadResult.Error)
                                } else {
                                    _imageDownloadState.emit(ImageDownLoadResult.NoneStart)
                                }
                            }
                        }
                    }
            }
            .enqueue(workRequest)
    }
}