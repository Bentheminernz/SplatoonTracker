package nz.benlawrence.splatoontracker.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import nz.benlawrence.splatoontracker.R
import nz.benlawrence.splatoontracker.data.models.Splatoon3Ink.EventScheduleNode
import nz.benlawrence.splatoontracker.ui.theme.BlitzFontFamily
import nz.benlawrence.splatoontracker.utils.getBattleImage

@Composable
fun ChallengeCard(
  challenge: EventScheduleNode
) {
  Column(
    modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(MaterialTheme.colorScheme.surfaceVariant)
        .padding(16.dp)
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      Image(
        painter = painterResource(R.drawable.challenge),
        contentDescription = "Challenge icon"
      )

      Column {
        Text(
          text = challenge.leagueMatchSetting.leagueMatchEvent.name,
          fontFamily = BlitzFontFamily,
          fontWeight = FontWeight.Bold
        )

        Text(
          text = challenge.leagueMatchSetting.leagueMatchEvent.desc,
          fontFamily = BlitzFontFamily
        )
      }
    }

    VsStageItem(
      vsStages = challenge.leagueMatchSetting.vsStages,
      vsRule = challenge.leagueMatchSetting.vsRule,
      startTime = challenge.timePeriods.first().startTime,
      endTime = challenge.timePeriods.first().endTime
    )

    Column {
      challenge.timePeriods.forEach { period ->
        Row {
          Image(
            painter = getBattleImage(challenge.leagueMatchSetting.vsRule.id),
            contentDescription = "Image of ${challenge.leagueMatchSetting.vsRule.name}",
          )

          Text(
            text = "Starts at ${period.startTime} - Ends at ${period.endTime}",
            fontFamily = BlitzFontFamily
          )
        }
      }
    }
  }
}