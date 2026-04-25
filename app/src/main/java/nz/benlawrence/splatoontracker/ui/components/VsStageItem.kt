package nz.benlawrence.splatoontracker.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import coil3.compose.AsyncImage
import nz.benlawrence.splatoontracker.data.models.VsRule
import nz.benlawrence.splatoontracker.data.models.VsStage
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun VsStageItem(
    vsStages: List<VsStage>,
    vsRule: VsRule,
    startTime: String,
    endTime: String
) {
    val formattedStartEnd by produceState(initialValue = "") {
        val formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH)
        val start = ZonedDateTime.parse(startTime).format(formatter)
        val end = ZonedDateTime.parse(endTime).format(formatter)
        value = "$start - $end"
    }

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Text(vsRule.name)

            Text(formattedStartEnd)
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            vsStages.forEach { item ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    AsyncImage(
                        model = item.image.url,
                        contentDescription = "Image of ${item.name}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                    Text(item.name, textAlign = TextAlign.Center)
                }
            }
        }
    }
}