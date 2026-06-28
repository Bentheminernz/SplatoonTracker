package nz.benlawrence.splatoontracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.salmonrun.EnemyResult

@Composable
fun EnemyResultCard(
  results: List<EnemyResult>
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .background(MaterialTheme.colorScheme.surfaceVariant)
  ) {
    results.forEach { result ->
      Row(
        verticalAlignment = Alignment.CenterVertically,
      ) {
        AsyncImage(
          model = result.enemy.image.url,
          contentDescription = "Image of ${result.enemy.name}",
          modifier = Modifier.size(48.dp)
        )

        Text(
          text = result.enemy.name,
          modifier = Modifier.padding(start = 8.dp)
        )

        Text(
          " ${result.teamDefeatCount} (${result.defeatCount}) / Appearances x${result.popCount}"
        )
      }
    }
  }
}