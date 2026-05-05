package nz.benlawrence.splatoontracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import nz.benlawrence.splatoontracker.R
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.Badge
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.battles.Award
import nz.benlawrence.splatoontracker.ui.theme.BlitzFontFamily

@Composable
fun VsBattleBadgePlate(
  award: Award
) {
  val image = when(award.rank) {
    "GOLD" -> R.drawable.gold
    "SILVER" -> R.drawable.silver
    else -> R.drawable.ic_home
  }

  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    modifier = Modifier
      .fillMaxWidth()
      .rotate(-2f)
      .clip(RoundedCornerShape(16.dp))
      .background(MaterialTheme.colorScheme.surfaceVariant)
      .padding(16.dp)
  ) {
    AsyncImage(
      model = image,
      contentDescription = "Image of ${award.rank} badge",
    )

    Text(
      text = award.name,
      fontFamily = BlitzFontFamily
    )
  }
}