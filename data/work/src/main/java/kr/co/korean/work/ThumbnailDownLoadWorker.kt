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
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

// TODO: 이미지 다운로드 실패했을때 에러처리?
private const val JPG_EXTENSION = "jpg"
private const val JPG_MIME_TYPE = "image/jpg"
@HiltWorker
class ThumbnailDownLoadWorker @AssistedInject constructor(
    @Assisted applicationContext: Context,
    @Assisted private val workerParams: WorkerParameters,
): CoroutineWorker(applicationContext, workerParams) {
    override suspend fun doWork(): Result {
        val url = checkNotNull(workerParams.inputData.getString("url")) {
            return Result.failure()
        }

        return try {
            downloadImageToGallery(url.convertToBitmap())

            Result.success()
        } catch (e: IOException) {
            Result.failure()
        } catch (e: MalformedURLException) {
            Result.failure()
        } catch (e: Exception) {
            Result.failure()
        }
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
            Log.e("imageSaveLog", "saved")
        }
    }
}