package nz.benlawrence.splatoontracker.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import nz.benlawrence.splatoontracker.R
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.salmonrun.WaveResultDetail

@Composable
fun WaveCard(
  wave: WaveResultDetail
) {
  val waveType = when (wave.waterLevel) {
    0 -> "Low Tide"
    1 -> "Normal"
    2 -> "High Tide"
    else -> "-"
  }

  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
      .padding(8.dp)
      .background(MaterialTheme.colorScheme.surfaceVariant)
      .padding(8.dp)
  ) {
    Text("Wave ${wave.waveNumber}")

    Text("${wave.teamDeliverCount} / ${wave.deliverNorm}")

    Text(text = waveType)

    Text(wave.eventWave?.name ?: "-")

    Row(
      verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
      Image(
        painter = painterResource(id = R.drawable.gold_egg),
        contentDescription = "A Golden Egg"
      )

      Text("x${wave.goldenPopCount}")
    }
  }
}