package nz.benlawrence.splatoontracker.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.*
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.*
import androidx.glance.text.*
import androidx.glance.unit.ColorProvider
import coil3.Bitmap
import coil3.compose.AsyncImage
import coil3.imageLoader
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.request.allowHardware
import nz.benlawrence.splatoontracker.MainActivity
import nz.benlawrence.splatoontracker.data.SplatoonAPIClient
import nz.benlawrence.splatoontracker.data.models.Data
import android.graphics.drawable.BitmapDrawable
import android.graphics.fonts.Font
import android.util.Log
import coil3.BitmapImage
import coil3.toBitmap
import nz.benlawrence.splatoontracker.R
import nz.benlawrence.splatoontracker.ui.theme.BlitzFontFamily


class SplatoonWidget : GlanceAppWidget() {
  override suspend fun provideGlance(context: Context, id: GlanceId) {
    val state = try {
      val response = SplatoonAPIClient.splattonAPI.getSchedules()
      WidgetState.Success(response.data)
    } catch (e: Exception) {
      WidgetState.Error(e.message ?: "Failed to load")
    }

    val stages = if (state is WidgetState.Success) {
      state.data.regularSchedules?.nodes
        ?.firstOrNull()
        ?.regularMatchSetting
        ?.vsStages
        ?.map { stage ->
          Log.d("SplatoonWidget", "Stage URL: ${stage.image.url}")
          loadBitmap(context, stage.image.url)
        }
        ?: emptyList()
    } else emptyList()

    Log.d("SplatoonWidget", "Stages loaded: ${stages.size}, nulls: ${stages.count { it == null }}")
    provideContent {
      SplatoonWidgetContent(state, stages)
    }
  }
}

suspend fun loadBitmap(context: Context, url: String): Bitmap? {
  val request = ImageRequest.Builder(context)
    .data(url)
    .allowHardware(false)
    .build()
  return when (val result = context.imageLoader.execute(request)) {
    is SuccessResult -> {
      Log.d("SplatoonWidget", "Image type: ${result.image::class.simpleName}")
      result.image.toBitmap()
    }

    else -> {
      Log.e("SplatoonWidget", "Failed to load bitmap: $result")
      null
    }
  }
}

@Composable
fun SplatoonWidgetContent(state: WidgetState, stages: List<Bitmap?> = emptyList()) {
  Box(
    modifier = GlanceModifier
      .fillMaxSize()
      .background(GlanceTheme.colors.widgetBackground)
      .padding(12.dp)
      .clickable(actionStartActivity<MainActivity>())
  ) {
    when (state) {
      is WidgetState.Success -> {
        // Adapt this to your actual Data model fields
        val rotation = state.data.regularSchedules?.nodes?.firstOrNull()
        Column(modifier = GlanceModifier.fillMaxSize()) {
          Row {
            Image(
              provider = ImageProvider(R.drawable.regular_battle),
              contentDescription = "Regular Battle Icon",
              modifier = GlanceModifier.size(24.dp)
            )

            Text(
              text = "Current Rotation",
              style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = GlanceTheme.colors.onSurface
              )
            )
          }
          Spacer(modifier = GlanceModifier.height(4.dp))
          Row(modifier = GlanceModifier.fillMaxWidth()) {
            rotation?.regularMatchSetting?.vsStages?.forEachIndexed { index, stage ->
              val bitmap = stages.getOrNull(index)
              Column(modifier = GlanceModifier.defaultWeight()) {
                Text(
                  text = stage.name,
                  style = TextStyle(fontSize = 12.sp, color = GlanceTheme.colors.onSurface)
                )
                if (bitmap != null) {
                  Image(
                    provider = ImageProvider(bitmap),
                    contentDescription = "Stage image for ${stage.name}",
                    modifier = GlanceModifier
                      .height(60.dp)
                  )
                } else {
                  Text("nah")
                }
              }
            }
          }
        }
      }

      is WidgetState.Error -> {
        Text(
          text = "Error: ${state.message}",
        )
      }

      WidgetState.Loading -> {
        Text("Loading...")
      }
    }
  }
}

sealed class WidgetState {
  object Loading : WidgetState()
  data class Success(val data: Data) : WidgetState()
  data class Error(val message: String) : WidgetState()
}