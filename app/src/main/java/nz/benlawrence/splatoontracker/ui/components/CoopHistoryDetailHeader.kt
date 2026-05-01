package nz.benlawrence.splatoontracker.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import nz.benlawrence.splatoontracker.R
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.salmonrun.Boss
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.salmonrun.BossResult
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.salmonrun.CoopStage
import nz.benlawrence.splatoontracker.ui.theme.BlitzFontFamily
import nz.benlawrence.splatoontracker.utils.formatDDMMYYYY

@Composable
fun CoopHistoryDetailHeader(
  coopStage: CoopStage,
  resultWave: Int,
  playedTime: String,
  boss: Boss? = null
) {
  val didWin by remember {
    derivedStateOf { resultWave >= 2 }
  }

  val bossImage = when (boss?.id) {
    "Q29vcEVuZW15LTIz" -> R.drawable.cohozuna
    "Q29vcEVuZW15LTI0" -> R.drawable.horrorboros
    "Q29vcEVuZW15LTI1" -> R.drawable.cohozuna
    "Q29vcEVuZW15LTMw" -> R.drawable.triumvirate
    else -> R.drawable.ic_launcher_foreground
  }

  Box(
    modifier = Modifier
      .fillMaxWidth()
  ) {
    if (coopStage.image?.url != null) {
      AsyncImage(
        model = coopStage.image.url,
        contentDescription = "Image of ${coopStage.name}",
        contentScale = ContentScale.Crop,
        modifier = Modifier
          .fillMaxWidth()
          .height(130.dp)
      )
    }

    Column(
      modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = formatDDMMYYYY(playedTime),
          fontFamily = BlitzFontFamily,
          textAlign = TextAlign.Left
        )

        if (boss != null && bossImage != R.drawable.ic_launcher_foreground) {
          Image(
            painter = painterResource(id = bossImage),
            contentDescription = "Image of ${boss.name}",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(48.dp)
          )
        }
      }

      Text(
        text = "${if (didWin) "Victory" else "Defeat"} at ${coopStage.name}",
        fontFamily = BlitzFontFamily,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        fontSize = 18.sp,
        color = Color.White,
        modifier = Modifier
          .fillMaxWidth()
      )
    }
  }
}