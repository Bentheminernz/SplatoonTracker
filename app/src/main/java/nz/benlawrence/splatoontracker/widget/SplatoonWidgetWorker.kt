package nz.benlawrence.splatoontracker.widget

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.glance.appwidget.updateAll
import androidx.work.*
import nz.benlawrence.splatoontracker.MainActivity
import nz.benlawrence.splatoontracker.R
import nz.benlawrence.splatoontracker.data.SplatoonAPIClient
import nz.benlawrence.splatoontracker.data.models.RegularScheduleNode
import java.util.concurrent.TimeUnit

class SplatoonWidgetWorker(
  private val context: Context,
  params: WorkerParameters
) : CoroutineWorker(context, params) {

  override suspend fun doWork(): Result {
    return try {
      val response = SplatoonAPIClient.splattonAPI.getSchedules()
      val newRotation = response.data.regularSchedules?.nodes?.firstOrNull()

      val prefs = context.getSharedPreferences("widget_prefs", Context.MODE_PRIVATE)
      val lastStageKey = prefs.getString("last_stage_key", null)

      val currentStageKey = newRotation?.regularMatchSetting?.vsStages
        ?.joinToString(",") { it.name }

      if (currentStageKey != null && currentStageKey != lastStageKey) {
        sendRotationNotification(context, newRotation)
        prefs.edit().putString("last_stage_key", currentStageKey).apply()
      }

      SplatoonWidget().updateAll(context)

      Result.success()
    } catch (e: Exception) {
      Result.retry()
    }
  }

  companion object {
    private const val WORK_NAME = "SplatoonWidgetRefresh"

    fun schedule(context: Context) {
      val request = PeriodicWorkRequestBuilder<SplatoonWidgetWorker>(15, TimeUnit.MINUTES)
        .setConstraints(
          Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        )
        .build()

      WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        WORK_NAME,
        ExistingPeriodicWorkPolicy.KEEP,
        request
      )
    }
  }
}

private const val CHANNEL_ID = "rotation_notifications"
private const val CHANNEL_NAME = "Map Rotation Alerts"
private const val NOTIFICATION_ID = 1001

fun sendRotationNotification(context: Context, rotation: RegularScheduleNode?) {
  val stages = rotation?.regularMatchSetting?.vsStages
    ?.joinToString(" & ") { it.name }
    ?: ""

  val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)
      as NotificationManager

  // Create channel (safe to call repeatedly, no-ops if already exists)
  val channel = NotificationChannel(
    CHANNEL_ID,
    CHANNEL_NAME,
    NotificationManager.IMPORTANCE_DEFAULT
  ).apply {
    description = "Notifies when Splatoon map rotation changes"
  }
  notificationManager.createNotificationChannel(channel)

  // Tap notification -> open app
  val intent = Intent(context, MainActivity::class.java).apply {
    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
  }
  val pendingIntent = PendingIntent.getActivity(
    context,
    0,
    intent,
    PendingIntent.FLAG_IMMUTABLE
  )

  val notification = NotificationCompat.Builder(context, CHANNEL_ID)
    .setSmallIcon(R.drawable.ic_launcher_foreground) // swap for your own icon
    .setContentTitle("Multiplayer maps and modes have been updated!")
    .setContentText("$stages have rotated in!")
    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    .setContentIntent(pendingIntent)
    .setAutoCancel(true)
    .build()

  notificationManager.notify(NOTIFICATION_ID, notification)
}