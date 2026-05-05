package nz.benlawrence.splatoontracker.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.navigation.NavController
import nz.benlawrence.splatoontracker.data.CoralDataViewModel
import nz.benlawrence.splatoontracker.data.DataState
import nz.benlawrence.splatoontracker.ui.components.NameplateItem
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

  Column {
    when (val data = viewModel.dataState.bankaraHistoryDetails[id] ?: DataState.Loading) {
      is DataState.Loading -> Text("Loading...")
      is DataState.Error -> Text("Error: ${data.message}")
      is DataState.Success -> {
        val detail = data.data.battleDetail.vsHistoryDetail
        val didWin = detail.myTeam.judgement == "WIN"
        Text("Stage: ${detail.vsStage.name}")

        detail.myTeam.players.forEach { player ->
          VsBattlePlayerCard(player = player)
        }
      }
    }
  }
}