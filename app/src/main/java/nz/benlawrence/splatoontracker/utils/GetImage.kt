package nz.benlawrence.splatoontracker.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import nz.benlawrence.splatoontracker.R

@Composable
fun getBattleImage(id: String): Painter {
  val resId = when (id) {
    "VnNSdWxlLTI=" -> R.drawable.tower_control
    "VnNSdWxlLTQ=" -> R.drawable.clam_blitz
    "VnNSdWxlLTM=" -> R.drawable.rainmaker
    "VnNSdWxlLTE=" -> R.drawable.splat_zones
    "VnNSdWxlLTA=" -> R.drawable.turf_war
    else -> R.drawable.ic_home
  }
  return painterResource(resId)
}

@Composable
fun getScheduleImage(typename: String): Painter {
  val resId = when (typename) {
    "RegularMatchSetting" -> R.drawable.regular_battle
    "BankaraMatchSetting" -> R.drawable.bankara_battle
    "BankaraMatchSettingChallenge" -> R.drawable.bankara_battle
    "BankaraMatchSettingOpen" -> R.drawable.bankara_battle
    "XMatchSetting" -> R.drawable.x_battle
    else -> R.drawable.ic_home
  }
  return painterResource(resId)
}