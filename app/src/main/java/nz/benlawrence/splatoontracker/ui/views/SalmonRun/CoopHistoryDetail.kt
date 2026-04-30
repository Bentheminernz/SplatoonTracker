package nz.benlawrence.splatoontracker.ui.views.SalmonRun

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.navigation.NavController
import nz.benlawrence.splatoontracker.data.CoralDataViewModel
import nz.benlawrence.splatoontracker.data.DataState
import nz.benlawrence.splatoontracker.data.models.coral.splatnet.salmonrun.Nameplate
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
    is DataState.Success -> Column {
      NameplateItem(
        player = data.data.coopHistoryDetail.myResult.player
      )

      LazyRow {
        items(data.data.coopHistoryDetail.waveResults) { wave ->
          WaveCard(wave = wave)
        }
      }

      Text("Coop History Detail for ID: $coopHistoryId")
      Text("Result: ${data.data.coopHistoryDetail}")
    }
  }
}