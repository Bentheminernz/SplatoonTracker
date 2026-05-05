package nz.benlawrence.splatoontracker.ui.views.SalmonRun

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nz.benlawrence.splatoontracker.R
import nz.benlawrence.splatoontracker.data.CoralDataViewModel
import nz.benlawrence.splatoontracker.data.DataState
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.salmonrun.CoopHistoryDetail
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.salmonrun.MemberResult
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.salmonrun.MemberResultDetail
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.salmonrun.toPlayerCardData
import nz.benlawrence.splatoontracker.ui.components.CoopHistoryDetailHeader
import nz.benlawrence.splatoontracker.ui.components.CoopHistoryPlayerCard
import nz.benlawrence.splatoontracker.ui.components.EnemyResultCard
import nz.benlawrence.splatoontracker.ui.components.NameplateItem
import nz.benlawrence.splatoontracker.ui.components.WaveCard

@Composable
fun CoopHistoryDetail(
  coopHistoryId: String,
  viewModel: CoralDataViewModel,
  navController: NavController
) {
  LaunchedEffect(coopHistoryId) {
    if (viewModel.dataState.coopHistoryDetails[coopHistoryId] !is DataState.Success) {
      viewModel.getCoopHistoryDetail(coopHistoryId)
    }
  }

  when (val data = viewModel.dataState.coopHistoryDetails[coopHistoryId] ?: DataState.Loading) {
    is DataState.Loading -> CircularProgressIndicator()
    is DataState.Error -> Text("Error: ${data.message}")
    is DataState.Success ->
      Column(
        modifier = Modifier
          .verticalScroll(rememberScrollState())
      ) {
        val history = data.data.coopHistoryDetail

        CoopHistoryDetailHeader(
          coopStage = history.coopStage,
          resultWave = history.resultWave,
          playedTime = history.playedTime,
          weapons = history.weapons,
          boss = history.boss
        )

        LazyRow(
          horizontalArrangement = Arrangement.SpaceBetween,
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
          items(history.waveResults) { wave ->
            WaveCard(wave = wave)
          }
        }

        Column(
          verticalArrangement = Arrangement.spacedBy(8.dp),
          modifier = Modifier
            .padding(horizontal = 16.dp)
        ) {
          CoopHistoryPlayerCard(result = history.myResult.toPlayerCardData())
          history.memberResults.forEach { memberResultDetail ->
            CoopHistoryPlayerCard(result = memberResultDetail.toPlayerCardData())
          }

          EnemyResultCard(
            results = history.enemyResults
          )
        }
      }
  }
}