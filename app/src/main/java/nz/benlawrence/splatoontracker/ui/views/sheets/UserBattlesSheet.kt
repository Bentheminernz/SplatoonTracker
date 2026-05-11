package nz.benlawrence.splatoontracker.ui.views.sheets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nz.benlawrence.splatoontracker.data.DataState
import nz.benlawrence.splatoontracker.data.models.Splatoon3Ink.BankaraNode
import nz.benlawrence.splatoontracker.data.models.Splatoon3Ink.RegularScheduleNode
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.battles.HistoryGroups
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.battles.VsBattleHistories
import nz.benlawrence.splatoontracker.ui.views.MatchType
import nz.benlawrence.splatoontracker.utils.toTitleCase

@Composable
fun UserBattlesSheet(
  matchType: MatchType,
  regularState: DataState<VsBattleHistories>,
  bankaraState: DataState<VsBattleHistories>,
  currentRegular: RegularScheduleNode,
  currentBankara: BankaraNode,
  navController: NavController
) {
  Column(modifier = Modifier.padding(horizontal = 16.dp)) {
    val pathType = when (matchType) {
      MatchType.Regular -> "regular"
      MatchType.BankaraOpen -> "bankara_open"
      MatchType.BankaraChallenge -> "bankara_challenge"
      MatchType.XBattle -> "xbattle"
    }

    when (matchType) {
      MatchType.BankaraOpen -> DataStateContent(bankaraState) { data ->
        val currentStageIds = currentBankara.bankaraMatchSettings
          .filter { it.bankaraMode == "OPEN" }
          .flatMap { it.vsStages }
          .map { it.id }
          .toSet()

        val battles = data.historyGroups.nodes
          .flatMap { it.historyDetails.nodes }
          .filter { it.vsStage.id in currentStageIds }

        Text("Showing battles for current Bankara Open stages")
        BattleList(
          battles = battles,
          emptyText = "No battles found for current maps.",
          onBattleClick = { navController.navigate("vsbattle/$pathType/detail/${it.id}") },
          battleLabel = { "${it.judgement.toTitleCase()} at ${it.vsStage.name}" }
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
        Text("Showing all battles for Bankara Open")
        BattleList(
          battles = data.historyGroups.nodes
            .flatMap { it.historyDetails.nodes }
            .filter { it.vsStage.id !in currentStageIds },
          emptyText = "No battles found for Bankara Open.",
          onBattleClick = { navController.navigate("vsbattle/$pathType/detail/${it.id}") },
          battleLabel = { "${it.judgement.toTitleCase()} at ${it.vsStage.name}" }
        )
      }

      MatchType.BankaraChallenge -> DataStateContent(bankaraState) { data ->
        val currentStageIds = currentBankara.bankaraMatchSettings
          .filter { it.bankaraMode == "CHALLENGE" }
          .flatMap { it.vsStages }
          .map { it.id }
          .toSet()

        val battles = data.historyGroups.nodes
          .flatMap { it.historyDetails.nodes }
          .filter { it.vsStage.id in currentStageIds }

        Text("Showing battles for current Bankara Challenge stages")
        BattleList(
          battles = battles,
          emptyText = "No battles found for current maps.",
          onBattleClick = { navController.navigate("vsbattle/$pathType/detail/${it.id}") },
          battleLabel = { "${it.judgement.toTitleCase()} at ${it.vsStage.name}" }
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
        Text("Showing all battles for Bankara Challenge")
        BattleList(
          battles = data.historyGroups.nodes
            .flatMap { it.historyDetails.nodes }
            .filter { it.vsStage.id !in currentStageIds },
          emptyText = "No battles found for Bankara Challenge.",
          onBattleClick = { navController.navigate("vsbattle/$pathType/detail/${it.id}") },
          battleLabel = { "${it.judgement.toTitleCase()} at ${it.vsStage.name}" }
        )
      }

      MatchType.Regular -> DataStateContent(regularState) { data ->
        val currentStageIds = currentRegular.regularMatchSetting.vsStages.map { it.id }.toSet()
        val battles = data.historyGroups.nodes
          .flatMap { it.historyDetails.nodes }
          .filter { it.vsStage.id in currentStageIds }

        Text("Your history for current maps:")
        BattleList(
          battles = battles,
          emptyText = "No regular battles found.",
          onBattleClick = { navController.navigate("vsbattle/$pathType/detail/${it.id}") },
          battleLabel = { "${it.judgement.toTitleCase()} at ${it.vsStage.name}" }
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

         Text("All battles:")
         BattleList(
          battles = data.historyGroups.nodes
            .flatMap { it.historyDetails.nodes }
            .filter { it.vsStage.id !in currentStageIds },
          emptyText = "No regular battles found.",
          onBattleClick = { navController.navigate("vsbattle/$pathType/detail/${it.id}") },
          battleLabel = { "${it.judgement.toTitleCase()} at ${it.vsStage.name}" }
         )
      }

      else -> Text("Not implemented yet for $matchType")
    }
  }
}

@Composable
fun <T> DataStateContent(
  state: DataState<T>,
  content: @Composable (T) -> Unit
) {
  when (state) {
    is DataState.Success -> content(state.data)
    is DataState.Loading -> CircularProgressIndicator()
    is DataState.Error -> Text("Error loading battles: ${state.message}")
  }
}

@Composable
fun BattleList(
  battles: List<HistoryGroups.Node.HistoryDetails.Node>,
  emptyText: String,
  onBattleClick: (HistoryGroups.Node.HistoryDetails.Node) -> Unit,
  battleLabel: (HistoryGroups.Node.HistoryDetails.Node) -> String
) {
  LazyColumn {
    if (battles.isEmpty()) {
      item { Text(emptyText) }
    } else {
      items(battles) { battle ->
        Button(onClick = { onBattleClick(battle) }) {
          Text(battleLabel(battle))
        }
      }
    }
  }
}