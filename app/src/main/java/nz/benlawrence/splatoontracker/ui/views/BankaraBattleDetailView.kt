package nz.benlawrence.splatoontracker.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nz.benlawrence.splatoontracker.data.CoralDataViewModel
import nz.benlawrence.splatoontracker.data.DataState
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.battles.BattleDetailPlayer
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.battles.Player
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.battles.VsTeam
import nz.benlawrence.splatoontracker.ui.components.GearItem
import nz.benlawrence.splatoontracker.ui.components.NameplateItem
import nz.benlawrence.splatoontracker.ui.components.VsBattleBadgePlate
import nz.benlawrence.splatoontracker.ui.components.VsBattlePlayerCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BankaraBattleDetailView(
  id: String,
  viewModel: CoralDataViewModel,
  navController: NavController
) {
  LaunchedEffect(id) {
    if (viewModel.dataState.bankaraHistoryDetails[id] !is DataState.Success) {
      viewModel.getBankaraBattleHistoryDetail(id)
    }
  }

  val selectedPlayer = remember { mutableStateOf<Player?>(null) }

  Column(
    modifier = Modifier
      .verticalScroll(rememberScrollState())
  ) {
    when (val data = viewModel.dataState.bankaraHistoryDetails[id] ?: DataState.Loading) {
      is DataState.Loading -> Text("Loading...")
      is DataState.Error -> Text("Error: ${data.message}")
      is DataState.Success -> {
        val detail = data.data.battleDetail.vsHistoryDetail
        val didWin = detail.myTeam.judgement == "WIN"
        val allTeams: List<VsTeam> =
          (listOf(detail.myTeam) + detail.otherTeams).sortedBy { it.order }

        Text("Stage: ${detail.vsStage.name}")

        allTeams.forEach { team ->
          Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
              .clip(RoundedCornerShape(16.dp))
              .background(MaterialTheme.colorScheme.surfaceVariant)
              .padding(8.dp)
          ) {
            Text(if (team.judgement == "WIN") "Winner" else "Defeat")
            team.players.forEach { player ->
              VsBattlePlayerCard(
                player = player,
                onClick = {
                  selectedPlayer.value = player
                }
              )
            }
          }
        }

        NameplateItem(player = detail.myTeam.players.first { it.id == detail.player.id })

        detail.awards.forEach { award ->
          VsBattleBadgePlate(award = award)
        }

        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          GearItem(gear = detail.player.headGear)
          GearItem(gear = detail.player.clothingGear)
          GearItem(gear = detail.player.shoesGear)
        }

        selectedPlayer.value?.let { player ->
          ModalBottomSheet(onDismissRequest = { selectedPlayer.value = null }) {
            Column {
              NameplateItem(player = player)

              Text("Gear Used")
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                GearItem(gear = player.headGear)
                GearItem(gear = player.clothingGear)
                GearItem(gear = player.shoesGear)
              }
            }
          }
        }
      }
    }
  }
}