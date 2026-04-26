package nz.benlawrence.splatoontracker.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import kotlinx.coroutines.delay
import nz.benlawrence.splatoontracker.data.models.VsRule
import nz.benlawrence.splatoontracker.data.models.VsStage
import nz.benlawrence.splatoontracker.ui.theme.BlitzFontFamily
import nz.benlawrence.splatoontracker.utils.getBattleImage
import java.time.Instant
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun VsStageItem(
  vsStages: List<VsStage>,
  vsRule: VsRule,
  startTime: String,
  endTime: String,
  isNext: Boolean = false
) {
  val formattedStartEnd by produceState(initialValue = "") {
    val formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH)
    val start = ZonedDateTime.parse(startTime).format(formatter)
    val end = ZonedDateTime.parse(endTime).format(formatter)
    value = "$start - $end"
  }

  val timeTill by produceState(initialValue = "") {
    while (true) {
      val end = Instant.parse(startTime)
      val duration = java.time.Duration.between(Instant.now(), end)
      val hours = duration.toHours()
      val minutes = duration.toMinutesPart()
      val seconds = duration.toSecondsPart()
      value = "%02d:%02d:%02d".format(hours, minutes, seconds)
      delay(1000)
    }
  }

  // Header row: rule icon + name on left, time info on right
  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      Image(
        painter = getBattleImage(vsRule.id),
        contentDescription = "Image of ${vsRule.name}",
        modifier = Modifier.size(28.dp)
      )
      Text(
        vsRule.name,
        fontFamily = BlitzFontFamily,
        fontSize = 16.sp
      )
    }

    Column(horizontalAlignment = Alignment.End) {
      if (isNext) {
        Text(
          "in $timeTill",
          fontFamily = BlitzFontFamily,
          fontSize = 14.sp,
          textAlign = TextAlign.End,
        )
      } else {
        Text(
          formattedStartEnd,
          fontFamily = BlitzFontFamily,
          fontSize = 14.sp,
          textAlign = TextAlign.End,
        )
      }
    }
  }

  // Stage images: joined corners so they look like one image
  Row(modifier = Modifier.fillMaxWidth()) {
    vsStages.forEachIndexed { index, stage ->
      val cornerRadius = 12.dp
      val shape = when {
        index == 0 -> RoundedCornerShape(
          topStart = cornerRadius,
          topEnd = 0.dp,
          bottomEnd = 0.dp,
          bottomStart = cornerRadius
        )

        index == vsStages.lastIndex -> RoundedCornerShape(
          topStart = 0.dp,
          topEnd = cornerRadius,
          bottomEnd = cornerRadius,
          bottomStart = 0.dp
        )

        else -> RoundedCornerShape(0.dp)
      }

      Column(modifier = Modifier.weight(1f)) {
        AsyncImage(
          model = stage.image.url,
          contentDescription = "Image of ${stage.name}",
          contentScale = ContentScale.Crop,
          modifier = Modifier
              .fillMaxWidth()
              .height(80.dp)
              .clip(shape)
        )
        Text(
          stage.name,
          fontFamily = BlitzFontFamily,
          fontSize = 12.sp,
          modifier = Modifier.padding(top = 4.dp)
        )
      }
    }
  }
}