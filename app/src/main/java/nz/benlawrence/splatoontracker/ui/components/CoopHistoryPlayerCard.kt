package nz.benlawrence.splatoontracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.salmonrun.Player
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.salmonrun.SpecialWeapon
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.salmonrun.WeaponDetail

data class PlayerCardData(
  val player: Player,
  val weapons: List<WeaponDetail>,
  val specialWeapon: SpecialWeapon,
  val defeatEnemyCount: Int,
  val goldenDeliverCount: Int,
  val rescueCount: Int,
  val deliverCount: Int
)
@Composable
fun CoopHistoryPlayerCard(
  result: PlayerCardData
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .background(MaterialTheme.colorScheme.surfaceVariant)
      .padding(8.dp)
  ) {
    Column {
      Text(result.player.name)
      Text("Boss Salmonids x${result.defeatEnemyCount}")
    }

    Column {
      LazyRow {
        items(result.weapons) { weapon ->
          AsyncImage(
            model = weapon.image.url,
            contentDescription = "Image of ${weapon.name}",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(32.dp)
          )
        }
      }

      AsyncImage(
        model = result.specialWeapon.image.url,
        contentDescription = "Image of ${result.specialWeapon.name}",
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(32.dp)
      )
    }

    Column {
      Row {
        Text("${result.goldenDeliverCount}")

        Text("${result.rescueCount}")
      }

      Row {
        Text("${result.deliverCount}")

        Text("${result.rescueCount}")
      }
    }
  }
}