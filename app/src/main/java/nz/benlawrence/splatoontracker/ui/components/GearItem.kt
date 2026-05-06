package nz.benlawrence.splatoontracker.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import nz.benlawrence.splatoontracker.R
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.Gear

@Composable
fun GearItem(
  gear: Gear
) {
  Column {
    AsyncImage(
      model = gear.image?.url ?: "",
      contentDescription = "Image of ${gear.name}",
      error = painterResource(id = R.drawable.gold)
    )

    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      AsyncImage(
        model = gear.primaryGearPower.image.url,
        contentDescription = "Image of ${gear.primaryGearPower.name}",
        contentScale = ContentScale.Crop,
        error = painterResource(id = R.drawable.gold),
        modifier = Modifier
          .size(24.dp)
      )

      gear.additionalGearPowers.forEach { gear ->
        AsyncImage(
          model = gear.image.url,
          contentDescription = "Image of ${gear.name}",
          contentScale = ContentScale.Crop,
          error = painterResource(id = R.drawable.gold),
          modifier = Modifier
            .size(20.dp)
        )
      }
    }
  }
}
