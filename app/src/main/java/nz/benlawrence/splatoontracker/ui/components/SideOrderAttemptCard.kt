package nz.benlawrence.splatoontracker.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.sideorder.SideOrderTryResult

@Composable
fun SideOrderAttemptCard(
  attempt: SideOrderTryResult
) {
  Row {
    Column {
      AsyncImage(
        model = attempt.palette.weapon.image.url,
        contentDescription = "Image of ${attempt.palette.weapon.name}",
        contentScale = ContentScale.Crop
      )

      Row {
        AsyncImage(
          model = attempt.subWeapon.image.url,
          contentDescription = "Image of ${attempt.subWeapon.name}",
          contentScale = ContentScale.Crop
        )

        AsyncImage(
          model = attempt.specialWeapon.image.url,
          contentDescription = "Image of ${attempt.specialWeapon.name}",
          contentScale = ContentScale.Crop
        )
      }
    }

    Column {
      Text("${attempt.floor}F | Clear! ${attempt.clearTimeSec}")
    }
  }
}