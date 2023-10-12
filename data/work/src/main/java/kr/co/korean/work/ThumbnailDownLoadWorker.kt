package kr.co.korean.work

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

private const val JPG_EXTENSION = "jpg"
private const val JPG_MIME_TYPE = "image/jpg"
const val ERROR_MESSAGE = "errorMessage"
@HiltWorker
class ThumbnailDownLoadWorker @AssistedInject constructor(
    @Assisted applicationContext: Context,
    @Assisted private val workerParams: WorkerParameters,
): CoroutineWorker(applicationContext, workerParams) {
    override suspend fun doWork(): Result {
        val url = checkNotNull(workerParams.inputData.getString("url")) {
            return Result.failure()
        }

        return withContext(Dispatchers.IO) {
            try {
                downloadImageToGallery(url.convertToBitmap())

                Result.success()

                /**
                 * 현재는 [ThumbnailDownloadDataSource]에 예외 데이터를 전달하나 이를 핸들링하진 않습니다.
                 * 다만, 실무 진행시엔 예외 관련 정보를 전달 및 이를 수신하여
                 * 핸들링하며 최종적으로 UI Layer까지 전달하게 됩니다.
                 */
            } catch (e: IOException) {
                Result.failure(Data.Builder().putString(ERROR_MESSAGE, e.message).build())
            } catch (e: MalformedURLException) {
                Result.failure(Data.Builder().putString(ERROR_MESSAGE, e.message).build())
            } catch (e: Exception) {
                Result.failure(Data.Builder().putString(ERROR_MESSAGE, e.message).build())
            }
        }
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return super.getForegroundInfo()
    }

    private fun String.convertToBitmap(): Bitmap {
        return (URL(this).openConnection() as HttpURLConnection).apply {
            connect()
        }.let { httpURLConnection ->
            httpURLConnection.inputStream.let { inputStream ->
                BufferedInputStream(inputStream).let { bufferedInputStream ->
                    BitmapFactory.decodeStream(bufferedInputStream)
                }
            }
        }
    }

    private fun downloadImageToGallery(bitmap: Bitmap) {
        val filename = "${System.currentTimeMillis()}.${JPG_EXTENSION}"
        val outputStream = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            applicationContext.contentResolver?.
            let { resolver ->
                ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, JPG_MIME_TYPE)
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }.let { contentValues ->
                    resolver.insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        contentValues
                    )?.let { imageUri ->
                        imageUri.let { resolver.openOutputStream(it) }
                    }
                }
            }
        } else {
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES
            ).let { imagesDir ->
                File(imagesDir, filename)
            }.let { image ->
                FileOutputStream(image)
            }
        }
        outputStream?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
    }
}