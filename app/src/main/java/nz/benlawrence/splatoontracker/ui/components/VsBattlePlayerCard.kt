package nz.benlawrence.splatoontracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.battles.Player
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.battles.Result

@Composable
fun VsBattlePlayerCard(
  player: Player,
) {
  val stats = player.result
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Row {
      AsyncImage(
        model = player.weapon.image2d.url,
        contentDescription = "Image of ${player.weapon.name}"
      )

      Text(
        text = player.name,
      )
    }

    Row {
      Text("${player.paint}p")

      if (stats != null) {
        Row(
          modifier = Modifier
            .background(Color.Black)
            .clip(RoundedCornerShape(8.dp))
        ) {
          Text(
            text = stats.kill.toString() ?: "-"
          )

          Text(
            text = stats.death.toString() ?: "-"
          )

          Text(
            text = stats.special.toString() ?: "-"
          )
        }
      }
    }
  }
}