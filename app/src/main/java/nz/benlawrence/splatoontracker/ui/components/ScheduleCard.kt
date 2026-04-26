package nz.benlawrence.splatoontracker.ui.components

import android.text.Layout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nz.benlawrence.splatoontracker.data.models.VsRule
import nz.benlawrence.splatoontracker.data.models.VsStage
import nz.benlawrence.splatoontracker.ui.theme.BlitzFontFamily
import nz.benlawrence.splatoontracker.utils.getScheduleImage

@Composable
fun ScheduleCard(
  typename: String,
  currentNode: ScheduleDisplayData,
  nextNode: ScheduleDisplayData,
  rotation: Float,
  onViewSchedule: () -> Unit,
) {
  Column(
    modifier = Modifier
        .rotate(rotation)
        .clip(RoundedCornerShape(16.dp))
        .background(MaterialTheme.colorScheme.surfaceVariant)
        .fillMaxWidth()
        .padding(16.dp)
  ) {
    MatchTypeHeader(
      source = MatchTypeOrTypename.Typename(typename)
    )

    VsStageItem(
      vsStages = currentNode.vsStages,
      vsRule = currentNode.vsRule,
      startTime = currentNode.startTime,
      endTime = currentNode.endTime
    )

    VsStageItem(
      vsStages = nextNode.vsStages,
      vsRule = nextNode.vsRule,
      startTime = nextNode.startTime,
      endTime = nextNode.endTime,
      isNext = true
    )

    Button(
      onClick = onViewSchedule,
      modifier = Modifier.padding(top = 8.dp)
    ) {
      Text(
        "All Upcoming Stages",
        fontFamily = BlitzFontFamily,
      )
    }
  }
}

data class ScheduleDisplayData(
  val vsStages: List<VsStage>,
  val vsRule: VsRule,
  val startTime: String,
  val endTime: String
)