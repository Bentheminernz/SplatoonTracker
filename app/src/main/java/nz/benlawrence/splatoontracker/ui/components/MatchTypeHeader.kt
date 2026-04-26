package nz.benlawrence.splatoontracker.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nz.benlawrence.splatoontracker.R
import nz.benlawrence.splatoontracker.ui.theme.BlitzFontFamily
import nz.benlawrence.splatoontracker.ui.views.MatchType
import nz.benlawrence.splatoontracker.utils.getScheduleImage

@Composable
fun MatchTypeHeader(
  source: MatchTypeOrTypename
) {
  val title = when (source) {
    is MatchTypeOrTypename.KnownType -> when (source.type) {
      is MatchType.Regular -> "Regular Battle"
      is MatchType.BankaraOpen -> "Anarchy Battle Open"
      is MatchType.BankaraChallenge -> "Anarchy Battle Series"
      is MatchType.XBattle -> "X Battle"
    }

    is MatchTypeOrTypename.Typename -> when (source.typename) {
      "RegularMatchSetting" -> "Regular Battle"
      "BankaraMatchSettingOpen" -> "Anarchy Battle Open"
      "BankaraMatchSettingChallenge" -> "Anarchy Battle Series"
      "XMatchSetting" -> "X Battle"
      else -> "Unknown"
    }
  }

  val painter = when (source) {
    is MatchTypeOrTypename.KnownType -> painterResource(
      when (source.type) {
        is MatchType.Regular -> R.drawable.regular_battle
        is MatchType.BankaraChallenge -> R.drawable.bankara_battle
        is MatchType.BankaraOpen -> R.drawable.bankara_battle
        is MatchType.XBattle -> R.drawable.x_battle
      }
    )

    is MatchTypeOrTypename.Typename -> getScheduleImage(source.typename)
  }

  Row(
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Image(
      painter = painter,
      contentDescription = "Icon for $title"
    )

    Text(
      title,
      fontFamily = BlitzFontFamily,
      fontWeight = FontWeight.Bold,
      fontSize = 22.sp
    )
  }
}

sealed interface MatchTypeOrTypename {
  data class KnownType(val type: MatchType) : MatchTypeOrTypename
  data class Typename(val typename: String) : MatchTypeOrTypename
}