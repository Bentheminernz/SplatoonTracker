package nz.benlawrence.splatoontracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.battles.Player
import nz.benlawrence.splatoontracker.ui.theme.BlitzFontFamily

@Composable
fun NameplateItem(
  player: Player
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
  ) {
    AsyncImage(
      model = player.nameplate.background.image.url,
      contentDescription = null,
      contentScale = ContentScale.Crop,
      modifier = Modifier.matchParentSize()
    )

    Column(modifier = Modifier.padding(8.dp)) {
      Text(
        text = player.byname,
        fontFamily = BlitzFontFamily,
        textAlign = TextAlign.Left
      )

      Text(
        text = player.name,
//        text = "Ben",
        fontFamily = BlitzFontFamily,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        fontSize = 32.sp,
        modifier = Modifier.fillMaxWidth()
      )

      Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(
          "#${player.nameId}",
          fontFamily = BlitzFontFamily,
          textAlign = TextAlign.Left
        )

        LazyRow {
          items(player.nameplate.badges.filterNotNull()) { badge ->
            AsyncImage(
              model = badge.image?.url ?: "",
              contentDescription = "Image of one of the users badges",
              modifier = Modifier.size(28.dp)
            )
          }
        }
      }
    }
  }
}