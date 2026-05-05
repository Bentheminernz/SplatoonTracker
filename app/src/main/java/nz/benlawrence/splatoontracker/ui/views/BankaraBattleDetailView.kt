package nz.benlawrence.splatoontracker.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.navigation.NavController
import nz.benlawrence.splatoontracker.data.CoralDataViewModel
import nz.benlawrence.splatoontracker.data.DataState
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.battles.VsTeam
import nz.benlawrence.splatoontracker.ui.components.NameplateItem
import nz.benlawrence.splatoontracker.ui.components.VsBattleBadgePlate
import nz.benlawrence.splatoontracker.ui.components.VsBattlePlayerCard

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
        val allTeams: List<VsTeam> = (listOf(detail.myTeam) + detail.otherTeams).sortedBy { it.order }

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
              VsBattlePlayerCard(player = player)
            }
          }
        }

        NameplateItem(player = detail.myTeam.players.first { it.id == detail.player.id })

        detail.awards.forEach { award ->
          VsBattleBadgePlate(award = award)
        }
      }
    }
  }
}