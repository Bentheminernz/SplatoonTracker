package nz.benlawrence.splatoontracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.VsStage
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.battles.MyTeam
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.battles.OtherTeam
import nz.benlawrence.splatoontracker.ui.theme.BlitzFontFamily

@Composable
fun VsBattleHeader(
  vsStage: VsStage,
  judgement: String,
  myTeam: MyTeam,
  otherTeam: OtherTeam
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

    // Calculate score ratio for proportional width
    val totalScore = myTeam.result.score + otherTeam.result.score
    val myTeamRatio = if (totalScore > 0) myTeam.result.score.toFloat() / totalScore else 0.5f

    // Ensure both weights are greater than zero (minimum 0.1f)
    val finalMyTeamRatio = myTeamRatio.coerceAtLeast(0.1f).coerceAtMost(0.9f)
    val finalOtherTeamRatio = 1f - finalMyTeamRatio

    // Convert team colors to Compose Color
    val myTeamColor = Color(
      red = (myTeam.color.r.toInt() and 0xFF) / 255f,
      green = (myTeam.color.g.toInt() and 0xFF) / 255f,
      blue = (myTeam.color.b.toInt() and 0xFF) / 255f,
      alpha = myTeam.color.a / 255f
    )

    val otherTeamColor = Color(
      red = (otherTeam.color.r.toInt() and 0xFF) / 255f,
      green = (otherTeam.color.g.toInt() and 0xFF) / 255f,
      blue = (otherTeam.color.b.toInt() and 0xFF) / 255f,
      alpha = otherTeam.color.a / 255f
    )

    Row(
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .align(Alignment.BottomCenter)
        .fillMaxWidth()
        .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
      // My Team Score Section
      Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
          .weight(finalMyTeamRatio)
          .background(myTeamColor)
          .padding(vertical = 6.dp)
      ) {
        Text(
          text = "Score: ${myTeam.result.score}",
          fontFamily = BlitzFontFamily,
          color = Color.White,
          fontSize = 18.sp
        )
      }

      // Other Team Score Section
      Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
          .weight(finalOtherTeamRatio)
          .background(otherTeamColor)
          .padding(vertical = 6.dp)
      ) {
        Text(
          text = "Score: ${otherTeam.result.score}",
          fontFamily = BlitzFontFamily,
          color = Color.White,
          fontSize = 18.sp
        )
      }
    }
  }
}