package nz.benlawrence.splatoontracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.battles.Player
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.battles.Result

@Composable
fun VsBattlePlayerCard(
  player: Player,
  onClick: (Player) -> Unit,
) {
  val stats = player.result
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .background(Color.White.copy(alpha = 0.1f))
      .padding(8.dp)
      .clickable {
        onClick(player)
      }
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      AsyncImage(
        model = player.weapon.image2d.url,
        contentDescription = "Image of ${player.weapon.name}",
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(48.dp)
      )

      Text(
        text = player.name,
      )
    }

    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Text("${player.paint}p")

      Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
          .clip(RoundedCornerShape(8.dp))
          .background(Color.Black)
      ) {
        Text(
          text = stats?.kill?.toString() ?: "-"
        )

        Text(
          text = stats?.death?.toString() ?: "-"
        )

        Text(
          text = stats?.special?.toString() ?: "-"
        )
      }
    }
  }
}