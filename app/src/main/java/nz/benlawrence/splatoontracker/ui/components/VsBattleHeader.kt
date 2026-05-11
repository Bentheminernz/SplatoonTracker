package nz.benlawrence.splatoontracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.VsStage
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.battles.HistoryGroups
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.battles.ResultX
import nz.benlawrence.splatoontracker.ui.theme.BlitzFontFamily
import nz.benlawrence.splatoontracker.utils.toTitleCase

@Composable
fun VsBattleHeader(
  vsStage: VsStage,
  judgement: String,
  myScore: Int,
  opponentScore: Int
) {
  Box(modifier = Modifier.fillMaxWidth()) {
    AsyncImage(
      model = vsStage.image.url,
      contentDescription = "Image of ${vsStage.name}",
      contentScale = ContentScale.Crop,
      modifier = Modifier
        .fillMaxWidth()
        .height(160.dp)
    )

    Text(
      text = vsStage.name,
      fontFamily = BlitzFontFamily,
      color = Color.White,
      fontSize = 13.sp,
      modifier = Modifier
        .align(Alignment.TopEnd)
        .padding(8.dp)
        .background(Color.Black, RoundedCornerShape(4.dp))
        .padding(horizontal = 8.dp, vertical = 4.dp)
    )

    Text(
      text = judgement.uppercase(),
      fontFamily = BlitzFontFamily,
      color = Color.White,
      fontSize = 28.sp,
      modifier = Modifier
        .align(Alignment.Center)
        .background(Color.Black, RoundedCornerShape(4.dp))
        .padding(horizontal = 24.dp, vertical = 6.dp)
    )

    Row(
      horizontalArrangement = Arrangement.SpaceBetween,
      modifier = Modifier
        .align(Alignment.BottomCenter)
        .fillMaxWidth()
        .background(Color(0xFF6B2D9E))
        .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
      Text(
        text = "Score: $myScore",
        fontFamily = BlitzFontFamily,
        color = Color.White,
        fontSize = 18.sp
      )
      Text(
        text = "Score: $opponentScore",
        fontFamily = BlitzFontFamily,
        color = Color.White,
        fontSize = 18.sp
      )
    }
  }
}