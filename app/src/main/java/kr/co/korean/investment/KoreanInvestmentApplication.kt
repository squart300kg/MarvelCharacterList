package kr.co.korean.investment

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.DelegatingWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

private const val BACKGROUND_WORK_NOTIFICATION_CHANNEL_NAME = "backgroundWorkNotificationChannelName"
private const val BACKGROUND_WORK_NOTIFICATION_CHANNEL_ID = "backgroundWorkNotificationChannelId"

@HiltAndroidApp
class KoreanInvestmentApplication: Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                BACKGROUND_WORK_NOTIFICATION_CHANNEL_ID,
                BACKGROUND_WORK_NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT,
            )
            val notificationManager: NotificationManager? =
                getSystemService(NOTIFICATION_SERVICE) as? NotificationManager

            notificationManager?.createNotificationChannel(channel)
        }
    }
}