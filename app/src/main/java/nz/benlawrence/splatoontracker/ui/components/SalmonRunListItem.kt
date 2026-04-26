package nz.benlawrence.splatoontracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import nz.benlawrence.splatoontracker.R
import nz.benlawrence.splatoontracker.data.models.CoopGroupingRegularScheduleNode
import nz.benlawrence.splatoontracker.ui.theme.SplatoonSalmonRun
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun SalmonRunListItem(
  item: CoopGroupingRegularScheduleNode
) {
  val startTime by produceState(initialValue = "") {
    val dateTime = ZonedDateTime.parse(item.startTime)
    val formatter = DateTimeFormatter.ofPattern("E, M/d, h:mm a", Locale.ENGLISH)
    value = dateTime.format(formatter)
  }

  val endTime by produceState(initialValue = "") {
    val dateTime = ZonedDateTime.parse(item.endTime)
    val formatter = DateTimeFormatter.ofPattern("M/d, h:mm a", Locale.ENGLISH)
    value = dateTime.format(formatter)
  }

  val bossImage = when (item.setting.boss.id) {
    "Q29vcEVuZW15LTIz" -> R.drawable.cohozuna
    "Q29vcEVuZW15LTI0" -> R.drawable.horrorboros
    "Q29vcEVuZW15LTI1" -> R.drawable.cohozuna
    "Q29vcEVuZW15LTMw" -> R.drawable.triumvirate
    else -> R.drawable.ic_launcher_foreground
  }

  Column(
    verticalArrangement = Arrangement.spacedBy(8.dp),
    modifier = Modifier
        .clip(RoundedCornerShape(16.dp))
        .background(MaterialTheme.colorScheme.surfaceVariant)
        .padding(16.dp)
        .fillMaxWidth()
  ) {
    Row(
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      AsyncImage(
        model = bossImage,
        contentDescription = "Image of ${item.setting.boss.name}",
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(32.dp)
      )

      Text("$startTime - $endTime")
    }

    Row(
      horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      AsyncImage(
        model = item.setting.coopStage.image.url,
        contentDescription = "Image of ${item.setting.coopStage.name}",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(64.dp)
            .clip(RoundedCornerShape(8.dp))
      )

      Column {
        Text(item.setting.coopStage.name)

        LazyRow {
          items(item.setting.weapons) { weapon ->
            AsyncImage(
              model = weapon.image.url,
              contentDescription = "Image of ${weapon.name}",
              contentScale = ContentScale.Crop,
              modifier = Modifier.size(48.dp)
            )
          }
        }
      }
    }
  }
}